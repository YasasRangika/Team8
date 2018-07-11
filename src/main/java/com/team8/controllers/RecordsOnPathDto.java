package com.team8.controllers;

import com.team8.model.BlackSpot;
import com.team8.model.CriticalPoint;
import com.team8.model.Incident;
import com.team8.model.RoadSigns;
import com.team8.model.SpeedLimit;

public class RecordsOnPathDto {
	
	private String self;
	private RoadSigns roadsigns;
	private Incident incident;
	private BlackSpot blackSpot;
	private CriticalPoint criticalPoint;
	private SpeedLimit speedLimit;
	
	
	public BlackSpot getBlackSpot() {
		return blackSpot;
	}

	public void setBlackSpot(BlackSpot blackSpot) {
		this.blackSpot = blackSpot;
	}

	public CriticalPoint getCriticalPoint() {
		return criticalPoint;
	}

	public void setCriticalPoint(CriticalPoint criticalPoint) {
		this.criticalPoint = criticalPoint;
	}

	public SpeedLimit getSpeedLimit() {
		return speedLimit;
	}

	public void setSpeedLimit(SpeedLimit speedLimit) {
		this.speedLimit = speedLimit;
	}

	public String getSelf() {
		return self;
	}

	public void setSelf(String self) {
		this.self = self;
	}

	public RoadSigns getRoadsigns() {
		return roadsigns;
	}
	
	public void setRoadsigns(RoadSigns roadsigns) {
		this.roadsigns = roadsigns;
	}
	
	public Incident getIncident() {
		return incident;
	}
	
	public void setIncident(Incident incident) {
		this.incident = incident;
	}
	
	
}
