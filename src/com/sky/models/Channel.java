package com.sky.models;

import java.util.List;
import java.util.ArrayList;

public class Channel {

	private String name;
	private List<Movie> movies;
	
	public Channel(String name, List<Movie> movies) {
		this.name = name;
		this.movies = (movies != null) ? movies : new ArrayList<Movie>();
	}
	
	public Channel(String name) {
		this(name, null);
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
	
	public void addMovie(Movie movie) {
		movies.add(movie);
	}

	public void removeAllMovies() {
		movies.clear();		
	}
	
}
