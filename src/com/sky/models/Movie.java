package com.sky.models;

import org.joda.time.LocalTime;
import org.joda.time.Minutes;

public class Movie {
	private int id;
	private String name;
	private LocalTime startTime;
	private LocalTime endTime;
	
	public Movie(int id, String name, LocalTime startTime, LocalTime endTime) {
		this.id = id;
		this.name = name;
		this.startTime = startTime;
		this.endTime = endTime;
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public LocalTime getStartTime() {
		return startTime;
	}
	
	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}
	
	public LocalTime getEndTime() {
		return endTime;
	}
	
	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}
	
	public int getMinuteDuration() {
		return (Minutes.minutesBetween(this.startTime, this.endTime).getMinutes()); 
	}
	
	public int getStartHour() {
		return this.startTime.getHourOfDay();
	}
	
	public int getStartMinutes() {
		return this.startTime.getMinuteOfHour();
	}
	
	public int getEndHour() {
		return this.endTime.getHourOfDay();
	}
	
	public int getEndMinutes() {
		return this.endTime.getMinuteOfHour();
	}
	
	
}
