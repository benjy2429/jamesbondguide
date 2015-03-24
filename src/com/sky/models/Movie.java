package com.sky.models;

import java.util.Date;

public class Movie {
	private int id;
	private String name;
	private Date startTime;
	private Date endTime;
	
	public Movie(int id, String name, Date startTime, Date endTime) {
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
	
	public Date getStartTime() {
		return startTime;
	}
	
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	public Date getEndTime() {
		return endTime;
	}
	
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	public double getHourDuration() {
		return (this.endTime.getTime() - this.startTime.getTime()) / (1000 * 60 * 60);
	}
	
	
}
