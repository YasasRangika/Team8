package com.team8.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team8.controllers.LatLngDto;
import com.team8.controllers.RecordsOnPathDto;
import com.team8.model.BlackSpot;
import com.team8.model.CriticalPoint;
import com.team8.model.Incident;
import com.team8.model.RoadSigns;
import com.team8.model.SpeedLimit;
import com.team8.repositories.BlackSpotRepository;
import com.team8.repositories.CriticalPointRepository;
import com.team8.repositories.IncidentRepository;
import com.team8.repositories.RoadSignsRepository;
import com.team8.repositories.SpeedLimitRepository;

@Service
public class RecordsOnPathService {
	
	@Autowired
	private RoadSignsRepository roadSignsRepository;
	@Autowired
	private IncidentRepository incidentRepository;
	@Autowired
	private BlackSpotRepository blackSpotRepository;
	@Autowired
	private CriticalPointRepository criticalPointRepository;
	@Autowired
	private SpeedLimitRepository speedLimitRepository;
	
	public RecordsOnPathDto updateMap(LatLngDto latLngDto) {
		
		RecordsOnPathDto recordsOnPathDto = new RecordsOnPathDto();
		Incident incident = new Incident();
		RoadSigns roadSigns = new RoadSigns();
		BlackSpot blackSpot = new BlackSpot();
		CriticalPoint criticalPoint = new CriticalPoint();
		SpeedLimit speedLimit = new SpeedLimit();
		
		if(roadSignsRepository.findByLatLan(latLngDto.getLat(), latLngDto.getLng()) != null | incidentRepository.findByLatLan(latLngDto.getLat(), latLngDto.getLng()) != null | blackSpotRepository.findBlackSpot(latLngDto.getLat(), latLngDto.getLng()) != null | criticalPointRepository.findCriticalPoint(latLngDto.getLat(), latLngDto.getLng()) != null | speedLimitRepository.findSpeedLimitPoint(latLngDto.getLat(), latLngDto.getLng()) != null) {
			
			recordsOnPathDto.setSelf("Exists");
			roadSigns = roadSignsRepository.findByLatLan(latLngDto.getLat(), latLngDto.getLng());
			recordsOnPathDto.setRoadsigns(roadSigns);
			incident = incidentRepository.findByLatLan(latLngDto.getLat(), latLngDto.getLng());
			recordsOnPathDto.setIncident(incident);
			blackSpot = blackSpotRepository.findBlackSpot(latLngDto.getLat(), latLngDto.getLng());
			recordsOnPathDto.setBlackSpot(blackSpot);
			criticalPoint = criticalPointRepository.findCriticalPoint(latLngDto.getLat(), latLngDto.getLng());
			recordsOnPathDto.setCriticalPoint(criticalPoint);
			speedLimit = speedLimitRepository.findSpeedLimitPoint(latLngDto.getLat(), latLngDto.getLng());
			recordsOnPathDto.setSpeedLimit(speedLimit);
			
			return recordsOnPathDto;
		}else{
			
			recordsOnPathDto.setSelf("Error");
			return recordsOnPathDto;
		}
	}
}
