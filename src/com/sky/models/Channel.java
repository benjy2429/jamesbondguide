package com.sky.models;

import java.util.List;
import java.util.ArrayList;

public class Channel {

	private String id;
	private String name;
	private List<Movie> movies;
	
	public Channel(String id, String name, List<Movie> movies) {
		this.id = id;
		this.name = name;
		this.movies = (movies != null) ? movies : new ArrayList<Movie>();
	}
	
	public Channel(String id, String name) {
		this(id, name, null);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Movie> getMovies() {
		return movies;
	}

	public void setMovies(List<Movie> movies) {
		this.movies = movies;
	}
	
}
