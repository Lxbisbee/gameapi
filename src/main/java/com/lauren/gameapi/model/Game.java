package com.lauren.gameapi.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Game {

	private int id;
	private String name;
	private String summary; 
	private double rating;
	private long releaseDate;
	private String coverUrl;
	private String console;
	private String condition;
	private String location;
	private GamePricing gamePricing;
	
	public Game(){}
	
	
	
	public Game(int id, String name, String summary, double rating, long releaseDate, String coverUrl, String console, String condition,
			String location, GamePricing gamePricing) {
		super();
		this.id = id;
		this.name = name;
		this.summary = summary;
		this.rating = rating;
		this.releaseDate = releaseDate;
		this.coverUrl = coverUrl;
		this.console = console;
		this.condition = condition;
		this.location = location;
		this.gamePricing = gamePricing;
	}



	public Game(int id, String name, String summary, double rating, long releaseDate, String coverUrl, String console, String condition, String location) {
		this.id = id;
		this.name = name;
		this.summary = summary;
		this.rating = rating;
		this.releaseDate = releaseDate;
		this.coverUrl = coverUrl;
		this.console = console;
		this.condition = condition;
		this.location = location;
	}
	
	


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getSummary() {
		return summary;
	}


	public void setSummary(String summary) {
		this.summary = summary;
	}


	public double getRating() {
		return rating;
	}


	public void setRating(double rating) {
		this.rating = rating;
	}


	public long getReleaseDate() {
		return releaseDate;
	}
	
	public String getReleaseDateText(){
		return new SimpleDateFormat("MMMM d, y").format(new Date(releaseDate));
	}


	public void setReleaseDate(long releaseDate) {
		this.releaseDate = releaseDate;
	}


	public String getCoverUrl() {
		return coverUrl;
	}


	public void setCoverUrl(String coverUrl) {
		this.coverUrl = coverUrl;
	}


	public String getConsole() {
		return console;
	}


	public void setConsole(String console) {
		this.console = console;
	}



	public GamePricing getGamePricing() {
		return gamePricing;
	}



	public void setGamePricing(GamePricing gamePricing) {
		this.gamePricing = gamePricing;
	}
	


	public String getCondition() {
		return condition;
	}


	public void setCondition(String condition) {
		this.condition = condition;
	}


	public String getLocation() {
		return location;
	}



	public void setLocation(String location) {
		this.location = location;
	}



	@Override
	public String toString() {
		return "Game [id=" + id + ", name=" + name + ", summary=" + summary + ", rating=" + rating + ", releaseDate="
				+ releaseDate + ", coverUrl=" + coverUrl + ", console=" + console + ", condition= " + condition + ", location " + location + ", gamePricing=" + gamePricing
				+ "]";
	}

}
