package com.team8.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class BlackSpot {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	private double latitude;
    private double longitude;
    private double radius;
    private String message;
    private int con;
    private String blackSpotId;
    
    
	public String getBlackSpotId() {
		return blackSpotId;
	}

	public void setBlackSpotId(String blackSpotId) {
		this.blackSpotId = blackSpotId;
	}

	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public double getLatitude() {
		return latitude;
	}
	
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
	public double getLongitude() {
		return longitude;
	}
	
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	public double getRadius() {
		return radius;
	}
	
	public void setRadius(double radius) {
		this.radius = radius;
	}
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public int getCondition() {
		return con;
	}
	
	public void setCondition(int condition) {
		this.con = condition;
	}
 
}
