package com.lauren.gameapi.dao;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;


public class MariaDbUtil {
	private static String connectionUrl = 
			"jdbc:mariadb://localhost:"; //Database info goes here.
	
	public static Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			connection = DriverManager.getConnection(connectionUrl);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return connection;
	}

	public static void main(String[] args) throws SQLException {
		Connection conn = MariaDbUtil.getConnection();
		
		if(null != conn) {
			System.out.println("A real connection!!!");
			
			DatabaseMetaData metaData = conn.getMetaData();
			ResultSet rs = metaData.getTables(null, null, "%", null);
			
			while (rs.next()) {
				System.out.println(rs.getString(3));
			}
		} else {
			System.out.println("Help!!! No Connection!!!");
		}
		
	}

}