package com.lauren.gameapi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.lauren.gameapi.model.Game;
import com.lauren.gameapi.model.GamePricing;

public class GameDaoImpl { 
	private static String getMyGamesSql = "SELECT * FROM games ";
	private static String getGameByKey = "SELECT * FROM games WHERE id = ?";
	private static String deleteGame = "DELETE  FROM games WHERE id = ?";
	private static String insertGame = 
		"INSERT INTO games (name, release_date, rating, cover_url, summary, console, game_condition, location) VALUES(?, ?, ?, ?, ?, ?, ?, ?) ";
	
	
	

	public List<Game> getAllGames() {
		
		List<Game> myGames = new ArrayList<Game>();
		ResultSet result = null;
		Statement statement = null;
	
		Connection connection = MariaDbUtil.getConnection();
			
		try {
			statement = connection.createStatement();
			result = statement.executeQuery(getMyGamesSql);
			
			while(result.next()) {
				Game game = getGameFromResultSet(result);
				List<GamePricing> pricing = getGamePricing(game.getName(), game.getConsole());
				if(pricing.isEmpty()) {
					game.setGamePricing(new GamePricing());
				}
				else {game.setGamePricing(pricing.get(0));
				}
				System.out.println(game.getName() + " -> " + game.getGamePricing().getProductName());
				myGames.add(game);
			}
			
		} catch (SQLException e) { 
			e.printStackTrace();
		} finally {
			close(result, statement, connection); 
		}
		
		return myGames;
	}

	public List<Game> getById(int id) {
		List<Game> myGames = new ArrayList<Game>();
			
		ResultSet result = null;
		PreparedStatement statement = null;

		Connection con = MariaDbUtil.getConnection();
		
		try {	
			statement = con.prepareStatement(getGameByKey);			
			statement.setInt(1, id);			
			result = statement.executeQuery();
			
			while(result.next()) {
				Game game = this.getGameFromResultSet(result);
				myGames.add(game);
			}			
		} catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
			close(result, statement, con);		
		}		
		return myGames;
	}

	public int delete(int id) {
		int rowsDeleted = 0;
		PreparedStatement statement = null;

		Connection con = MariaDbUtil.getConnection();
		
		try {
			statement = con.prepareStatement(deleteGame);	
			statement.setInt(1, id);			
			rowsDeleted = statement.executeUpdate();
			System.out.println("Games deleted: " + rowsDeleted);
			
		} catch (SQLException e) {			
			e.printStackTrace();
		} finally {
			close(null, statement, con);		
		}		
		return rowsDeleted;
	}

	
	// "INSERT INTO games (name, release_date, rating, cover_url, summary, console, game_condition) VALUES(?, ?, ?, ?, ?, ?, ?) ";
	public void add(Game newGame) {
		PreparedStatement statement = null;
		Connection con = MariaDbUtil.getConnection();
		
		try {
			statement = con.prepareStatement(insertGame);	
			if(newGame.getLocation()==null || newGame.getLocation().isEmpty()) {
				newGame.setLocation("LIBRARY");
			}
			
			statement.setString(1, newGame.getName());
			statement.setLong(2, newGame.getReleaseDate());
			statement.setDouble(3, newGame.getRating());
			statement.setString(4, newGame.getCoverUrl());
			statement.setString(5, newGame.getSummary());
			statement.setString(6, newGame.getConsole());
			statement.setString(7, newGame.getCondition());
			statement.setString(8, newGame.getLocation());
			
			int numberOfRows = statement.executeUpdate();
			System.out.println("Games added: " + numberOfRows);
		
		} 
		catch (SQLException e) { e.printStackTrace(); } 
		
		finally {
			try {
				//result.close();
				statement.close();
				con.close();
			} 
			catch (SQLException e) { e.printStackTrace(); }
		}
		
	}

	//TODO: get other columns from results set
	private Game getGameFromResultSet(ResultSet result) throws SQLException {
		Game game = new Game();
		game.setId(result.getInt("id"));
		game.setSummary(result.getString("summary"));
		game.setCoverUrl(result.getString("cover_url"));
		game.setRating(result.getDouble("rating"));
		game.setName(result.getString("name"));	
		game.setConsole(result.getString("console"));
		game.setCondition(result.getString("game_condition"));
		game.setReleaseDate(result.getLong("release_date"));
		game.setLocation(result.getString("location"));

		return game;
	}

	private void close(ResultSet result, Statement statement, Connection connection) {
		try {
			if(result != null) result.close();
			if(statement != null) statement.close();
			if(connection != null) connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static GamePricing getGamePricing(ResultSet resultSet) throws SQLException {		
		return new GamePricing(
			resultSet.getInt("id"),
			resultSet.getString("consoleName"),
			resultSet.getString("productName"),
			resultSet.getDouble("loosePrice"),
			resultSet.getDouble("cibPrice"),
			resultSet.getDouble("newPrice"),
			resultSet.getDouble("retailLooseBuy"),
			resultSet.getDouble("retailLooseSell"),
			resultSet.getDouble("retailCibBuy"),
			resultSet.getDouble("retailCibSell"),
			resultSet.getDouble("retailNewBuy"),
			resultSet.getDouble("retailNewSell"),
			resultSet.getDouble("gameStopPrice"),
			resultSet.getDouble("gameStopTradePrice")
		);
	}
	
	public List<GamePricing> getGamePricing(String gameTitle, String console) {
		List<GamePricing> list = new ArrayList<>();
		String sql = "select * from game_pricing where match(productName) against (? in natural language mode)";

		//if Console is not null, include in search
		if(console != null) sql+= " and consoleName = ?";
		
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		
		try {
			connection = MariaDbUtil.getConnection();
			statement = connection.prepareStatement(sql);
			statement.setString(1,gameTitle);
			
			//if Console is not null, include in search
			if(console != null) statement.setString(2, console);
			
			
			result = statement.executeQuery();
			while(result.next()) {
				list.add(getGamePricing(result));
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			close(result, statement, connection);
		}
		return list;
	}
	
	public List<Game> update(Game updatedGame) {
		List<Game> list = new ArrayList<>();
		String sql = "UPDATE games SET name=?, release_date=?, rating=?, cover_url=?, summary=?, console=?, game_condition=?, location=? WHERE id=?";
		Connection connection = null;
		PreparedStatement statement = null;
		
		try {
			connection = MariaDbUtil.getConnection();
			statement = connection.prepareStatement(sql);
			statement.setString(1, updatedGame.getName());
			statement.setLong(2, updatedGame.getReleaseDate());
			statement.setDouble(3, updatedGame.getRating());
			statement.setString(4, updatedGame.getCoverUrl());
			statement.setString(5, updatedGame.getSummary());
			statement.setString(6, updatedGame.getConsole());
			statement.setString(7, updatedGame.getCondition());
			statement.setString(8, updatedGame.getLocation());
			statement.setInt(9, updatedGame.getId());
			statement.executeUpdate();
			list.add(updatedGame);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {close(null, statement, connection);}
		
		return list;
	}
}
