package com.lauren.gameapi.service;

import java.util.List;

import com.lauren.gameapi.dao.GameDaoImpl;
import com.lauren.gameapi.model.Game;

public class GameService {

		GameDaoImpl dao = new GameDaoImpl();
		
		public List<Game> getAllGames() {
			
			return dao.getAllGames();
		}
		
		public List<Game> getById(int id) {
			return dao.getById(id);
		}
		

		public void delete(int id) {
			dao.delete(id);
		}

		public void add(Game newGame) {
			dao.add(newGame);
					
		}
		
		public List<Game> update(Game updatedGame) {
			return dao.update(updatedGame);
		}
	}