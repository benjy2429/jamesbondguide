package com.sky.models;

import org.joda.time.LocalTime;
import org.joda.time.Minutes;

/**
 * The Movie model represents a James Bond movie, which can belong to a channel
 * @author Ben Carr
 *
 */
public class Movie {
	private int id;
	private String name;
	// Use Joda rather than the built in DateTime as we don't care about the date
	private LocalTime startTime;
	private LocalTime endTime;
	
	/**
	 * Constructor that creates a new movie
	 * @param id	The ID of the movie as an integer
	 * @param name	The name/title of the movie
	 * @param startTime	The start time of the movie as a Joda LocalTime
	 * @param endTime	The end time of the movie as a Joda LocalTime
	 */
	public Movie(int id, String name, LocalTime startTime, LocalTime endTime) {
		this.id = id;
		this.name = name;
		this.startTime = startTime;
		this.endTime = endTime;
	}
	
	/**
	 * 
	 * @return The movie ID
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * 
	 * @return The name/title of the movie
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * 
	 * @param name The name/title of the movie
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 
	 * @return The start time of the movie as a Joda LocalTime
	 */
	public LocalTime getStartTime() {
		return startTime;
	}
	
	/**
	 * 
	 * @param startTime The start time of the movie as a Joda LocalTime
	 */
	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}
	
	/**
	 * 
	 * @return The end time of the movie as a Joda LocalTime
	 */
	public LocalTime getEndTime() {
		return endTime;
	}
	
	/**
	 * 
	 * @param endTime The end time of the movie as a Joda LocalTime
	 */
	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}
	
	/**
	 * Gets the duration of the movie in minutes
	 * @return An integer representing the duration of the movie in minutes
	 */
	public int getMinuteDuration() {
		return (Minutes.minutesBetween(this.startTime, this.endTime).getMinutes()); 
	}
	
	/**
	 * 
	 * @return The start hour of the movie as an integer
	 */
	public int getStartHour() {
		return this.startTime.getHourOfDay();
	}
	
	/**
	 * 
	 * @return The minutes past the hour that the movie starts as an integer
	 */
	public int getStartMinutes() {
		return this.startTime.getMinuteOfHour();
	}
	
	/**
	 * 
	 * @return The end hour of the movie as an integer
	 */
	public int getEndHour() {
		return this.endTime.getHourOfDay();
	}
	
	/**
	 * 
	 * @return The minutes past the hour that the movie ends as an integer
	 */
	public int getEndMinutes() {
		return this.endTime.getMinuteOfHour();
	}
	
	
}
