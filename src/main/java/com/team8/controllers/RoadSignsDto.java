package com.team8.controllers;

public class RoadSignsDto {
	private String self;
	private double latitude;
    private double longitude;
    private double radius;
    private String message;
    private String sign;
	
	public String getSelf() {
		return self;
	}
	
	public void setSelf(String self) {
		this.self = self;
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

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
	
	
}
