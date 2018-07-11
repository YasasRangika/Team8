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
import com.team8.services.UserService;

@RestController
@RequestMapping("/team8")
public class ApiController {
	@RequestMapping("/test")
	public ResponseEntity<?> testApp(){
		return ResponseEntity.ok("It's working!!!");
	}
	
	//----------------------Road Signs(start)-------------------------------//
	@Autowired
	RoadSignsService roadSignService;
	
	@RequestMapping(value="/sign", method=RequestMethod.POST)
	public ResponseEntity<?> addNewSign(@RequestBody RoadSignsDto roadSignsDto, UriComponentsBuilder uriComponentsBuilder){
		RoadSignsDto rsd = roadSignService.saveNewSign(roadSignsDto);
		
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
	
	
	
	//----------------------User(start)-------------------------------//
	
	@Autowired
	UserService userService;
	
	@RequestMapping(value="/user", method=RequestMethod.POST)
	public ResponseEntity<?> addUser(@RequestBody UserDto userDto, UriComponentsBuilder uriComponentsBuilder){
		UserDto usr = userService.addUser(userDto);
		
		if(usr.getSelf() == "Exists") {
			ExistsMessage msg = new ExistsMessage();
			msg.setStatus("409");
			msg.setMessage("User exists!!!");
			msg.setDevelperMessage("User already exists, try with different username,phonenumber or email.");
			return ResponseEntity.status(HttpStatus.CONFLICT).body(msg);
		}else { 
			return ResponseEntity.status(HttpStatus.CREATED).body(usr);
		}
		
	}
	
	@RequestMapping(value="/searchUser", method=RequestMethod.POST)
	public ResponseEntity<?> searchUser(@RequestBody UserDto userDto, UriComponentsBuilder uriComponentsBuilder){
		UserDto fusr = userService.getUser(userDto);
		
		if(fusr.getSelf() == "Exists") {
			return ResponseEntity.status(HttpStatus.FOUND).body(fusr);
		}else { 
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}
	//----------------------User(end)-------------------------------//
	
	
	
	//----------------------Incident(start)-------------------------------//
	
	@Autowired
	IncidentService incidentService;
	
	@RequestMapping(value="/incident", method=RequestMethod.POST)
	public ResponseEntity<?> incidentRecords(@RequestBody IncidentDto incidentDto, UriComponentsBuilder uriComponentsBuilder){
		IncidentDto incidentDto2 = incidentService.addIncident(incidentDto);
		
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
	
	@Autowired
	BlackSpotService blackSpotService;
	
	@RequestMapping(value="newblackspot", method=RequestMethod.POST)
	public ResponseEntity<?> addNewBS(@RequestBody BlackSpotDto blackSpotDto, UriComponentsBuilder uriComponentsBuilder){
		BlackSpotDto bsDto = blackSpotService.AddNewBS(blackSpotDto);
		if(bsDto.getSelf() == "Exists") {
			ExistsMessage msg = new ExistsMessage();
			msg.setStatus("409");
			msg.setMessage("BlackSpot exists!!!");
			msg.setDevelperMessage("BlackSpot already exists.");
			return ResponseEntity.status(HttpStatus.CONFLICT).body(msg);
		}else {
			return ResponseEntity.status(HttpStatus.CREATED).body(bsDto);	
		}
	}
	//----------------------Records On Path(end)-------------------------------//
	
	
	
	//----------------------Black Spots(start)-------------------------------//
	
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
	
	@RequestMapping(value="newCritical", method=RequestMethod.POST)
	public ResponseEntity<?> addNewCP(@RequestBody CriticalPointDto criticalPointDto, UriComponentsBuilder uriComponentsBuilder){
		CriticalPointDto cpDto = criticalPointService.addNewCriticalPoint(criticalPointDto);
		if(cpDto.getSelf() == "Exists") {
			ExistsMessage msg = new ExistsMessage();
			msg.setStatus("409");
			msg.setMessage("Critical point exists!!!");
			msg.setDevelperMessage("Critical point already exists.");
			return ResponseEntity.status(HttpStatus.CONFLICT).body(msg);
		}else {
			return ResponseEntity.status(HttpStatus.CREATED).body(cpDto);	
		}
	}
	
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
	
	@RequestMapping(value="addSpeedLimit", method=RequestMethod.POST)
	public ResponseEntity<?> addNewSL(@RequestBody SpeedLimitDto speedLimitDto, UriComponentsBuilder uriComponentsBuilder){
		SpeedLimitDto slDto = speedLimitService.addNewSpeedLimitPoint(speedLimitDto);
		if(slDto.getSelf() == "Exists") {
			ExistsMessage msg = new ExistsMessage();
			msg.setStatus("409");
			msg.setMessage("Speed limit exists!!!");
			msg.setDevelperMessage("Speed limit already exists.");
			return ResponseEntity.status(HttpStatus.CONFLICT).body(msg);
		}else {
			return ResponseEntity.status(HttpStatus.CREATED).body(slDto);	
		}
	}
	
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
