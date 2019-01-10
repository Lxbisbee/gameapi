package com.lauren.gameapi.controller;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.lauren.gameapi.model.Game;
import com.lauren.gameapi.service.GameService;

@Path("/v1/games")
public class GameController {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Game> getAllGames() {		
		GameService service = new GameService();
		return service.getAllGames();
	}

	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public void deleteById(@PathParam("id") int id) {
		new GameService().delete(id);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void add(Game newGame) {
		GameService service = new GameService();
		service.add(newGame);
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Game> update(Game updatedGame) {
		System.out.println(updatedGame);
		GameService service = new GameService();
		return service.update(updatedGame);
	}
}
