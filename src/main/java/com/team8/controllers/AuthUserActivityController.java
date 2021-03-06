package com.team8.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.team8.services.BlackSpotService;
import com.team8.services.CriticalPointService;
import com.team8.services.IncidentService;
import com.team8.services.RecordsOnPathService;
import com.team8.services.RoadSignsService;
import com.team8.services.SpeedLimitService;
import com.team8.services.TmpIncidentService;
import com.team8.services.TmpRoadSignsService;

@RestController
@RequestMapping("/user")
public class AuthUserActivityController {

	//----------------------Road Signs(start)-------------------------------//
	@Autowired
	TmpRoadSignsService tmproadSignService;
	@Autowired
	RoadSignsService roadSignService;
	
	@RequestMapping(value="/sign", method=RequestMethod.POST)
	public ResponseEntity<?> addNewSign(@RequestBody RoadSignsDto roadSignsDto, UriComponentsBuilder uriComponentsBuilder){
		RoadSignsDto rsd = tmproadSignService.saveNewSign(roadSignsDto);
		
		if(rsd.getSelf() == "Exists") {
			ExistsMessage msg = new ExistsMessage();
			msg.setStatus("409");
			msg.setMessage("A road sign "+rsd.getSign()+" in latitude = "+rsd.getLatitude()+" and longitude = "+rsd.getLongitude()+" is already exists!");
			msg.setDevelperMessage("New raod sign creation of "+rsd.getSign()+" is failed because "+rsd.getSign()+" in (lat,lng) = ("+rsd.getLatitude()+", "+rsd.getLongitude()+") is already exists.");
			return ResponseEntity.status(HttpStatus.CONFLICT).body(msg);
		}else {
			return ResponseEntity.status(HttpStatus.CREATED).body(rsd);
		}
	}
	
	@RequestMapping(value="findSign",method=RequestMethod.POST)
	public ResponseEntity<?> getRaodSigns(@RequestBody RoadSignsDto roadSignsDto, UriComponentsBuilder uriComponentsBuilder){
		RoadSignsDto roadSignsDto2 = roadSignService.getRoadSign(roadSignsDto);
		 
		if(roadSignsDto2.getSelf() == "Exists") {
			return ResponseEntity.status(HttpStatus.FOUND).body(roadSignsDto2);
		}else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}
	//----------------------Road Signs(end)-------------------------------//
	
	
	
	//----------------------Incident(start)-------------------------------//
	
	@Autowired
	IncidentService incidentService;
	TmpIncidentService tmpIncidentService;
	
	@RequestMapping(value="/incident", method=RequestMethod.POST)
	public ResponseEntity<?> incidentRecords(@RequestBody IncidentDto incidentDto, UriComponentsBuilder uriComponentsBuilder){
		IncidentDto incidentDto2 = tmpIncidentService.addIncident(incidentDto);
		
		if(incidentDto.getSelf() == "Exists") {
			ExistsMessage msg = new ExistsMessage();
			msg.setStatus("409");
			msg.setMessage("Incident exists!!!");
			msg.setDevelperMessage("Incident already exists.");
			return ResponseEntity.status(HttpStatus.CONFLICT).body(msg);
		}else {
			return ResponseEntity.status(HttpStatus.CREATED).body(incidentDto2);
		}
	}
	
	@RequestMapping(value="findIncident", method=RequestMethod.POST)
	public ResponseEntity<?> findIncident(@RequestBody IncidentDto incidentDto, UriComponentsBuilder uriComponentsBuilder){
		IncidentDto incidentDto2 = incidentService.getIncident(incidentDto);
		if(incidentDto2.getSelf() == "Exists") {
			return ResponseEntity.status(HttpStatus.FOUND).body(incidentDto2);
		}else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}
	//----------------------Incident(end)-------------------------------//
	
	
	
	//----------------------Records On Path(start)-------------------------------//
	
	@Autowired
	RecordsOnPathService recordOnPathService;
	@RequestMapping(value="records", method=RequestMethod.POST)
	public ResponseEntity<?> findRecordsOnPath(@RequestBody LatLngDto latLngDto, UriComponentsBuilder uriComponentsBuilder){
		RecordsOnPathDto recordsOnPathDto = recordOnPathService.updateMap(latLngDto);
		if(recordsOnPathDto.getSelf() == "Exists") {
			return ResponseEntity.status(HttpStatus.FOUND).body(recordsOnPathDto);
		}else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}
	
	//----------------------Records On Path(end)-------------------------------//
	
	
	//----------------------Black Spots(start)-------------------------------//
	
	@Autowired
	BlackSpotService blackSpotService;
	
	@RequestMapping(value="blackspot", method=RequestMethod.POST)
	public ResponseEntity<?> getBlackSpots(@RequestBody BlackSpotDto blackSpotDto, UriComponentsBuilder uriComponentsBuilder){
		
		BlackSpotDto blackSpotDto2 = blackSpotService.getBlackSpot(blackSpotDto);
		
		if(blackSpotDto2.getSelf() == "Exists") {
			return ResponseEntity.status(HttpStatus.FOUND).body(blackSpotDto2);
		}else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}
	//----------------------Black Spots(end)-------------------------------//
	
	
	
	//----------------------Critical Point(start)-------------------------------//
	
	@Autowired
	CriticalPointService criticalPointService;
	
	@RequestMapping(value="criticalPoint", method=RequestMethod.POST)
	public ResponseEntity<?> getCriticalPoint(@RequestBody CriticalPointDto criticalPointDto, UriComponentsBuilder uriComponentsBuilder){
		
		CriticalPointDto criticalPointDto2 = criticalPointService.getCriticalPoint(criticalPointDto);
		
		if(criticalPointDto2.getSelf() == "Exists") {
			return ResponseEntity.status(HttpStatus.FOUND).body(criticalPointDto2);
		}else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}
	//----------------------Critical Point(end)-------------------------------//
	
	
	
	//----------------------Speed Limit(start)-------------------------------//
	
	@Autowired
	SpeedLimitService speedLimitService;
	
	@RequestMapping(value="speedLimit", method=RequestMethod.POST)
	public ResponseEntity<?> getSpeedLimit(@RequestBody SpeedLimitDto speedLimitDto, UriComponentsBuilder uriComponentsBuilder){
		
		SpeedLimitDto speedLimitDto2 = speedLimitService.getSpeedLimitPoint(speedLimitDto);
		
		if(speedLimitDto2.getSelf() == "Exists") {
			return ResponseEntity.status(HttpStatus.FOUND).body(speedLimitDto2);
		}else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}
	//----------------------Speed Limit(end)-------------------------------//

}
