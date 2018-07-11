package com.team8.controllers;

public class IncidentDto {
	
	private String self;
	private String date;
	private String reporter;
	private String incident;
	private double lat;
	private double lng;
	private String accidentType;
	
	public String getSelf() {
		return self;
	}
	
	public void setSelf(String self) {
		this.self = self;
	}
	
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public String getReporter() {
		return reporter;
	}
	
	public void setReporter(String reporter) {
		this.reporter = reporter;
	}
	
	public String getIncident() {
		return incident;
	}
	
	public void setIncident(String incident) {
		this.incident = incident;
	}
	
	public double getLat() {
		return lat;
	}
	
	public void setLat(double lat) {
		this.lat = lat;
	}
	
	public double getLng() {
		return lng;
	}
	
	public void setLng(double lng) {
		this.lng = lng;
	}

	public String getAccidentType() {
		return accidentType;
	}

	public void setAccidentType(String accidentType) {
		this.accidentType = accidentType;
	}
	
	
}
