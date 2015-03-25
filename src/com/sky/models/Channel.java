package com.sky.models;

import java.util.List;
import java.util.ArrayList;

/**
 * The Channel model represents a James Bond channel, which can have many movies
 * @author Ben Carr
 *
 */
public class Channel {

	private String name;
	// Store the related movies in a list
	private List<Movie> movies;
	
	/**
	 * Constructor that also takes a list of movies
	 * @param name
	 * @param movies
	 */
	public Channel(String name, List<Movie> movies) {
		this.name = name;
		this.movies = (movies != null) ? movies : new ArrayList<Movie>();
	}
	
	/**
	 * Constructor that creates an empty list of movies
	 * @param name
	 */
	public Channel(String name) {
		this(name, null);
	}

	/**
	 * 
	 * @return The name of the channel 
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @param name The name of the channel
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 
	 * @return The list of movies for this channel
	 */
	public List<Movie> getMovies() {
		return movies;
	}

	/**
	 * 
	 * @param movies A list of movies for this channel
	 */
	public void setMovies(List<Movie> movies) {
		this.movies = movies;
	}
	
	/**
	 * Adds a new movie to the existing list of movies
	 * @param movie The movie to add
	 */
	public void addMovie(Movie movie) {
		movies.add(movie);
	}

	/**
	 * Removes all movies for this channel
	 */
	public void removeAllMovies() {
		movies.clear();		
	}
	
}
