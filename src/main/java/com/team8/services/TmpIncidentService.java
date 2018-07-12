package com.team8.services;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team8.controllers.IncidentDto;
import com.team8.model.TmpIncident;
import com.team8.repositories.TmpIncidentRepository;

@Service
public class TmpIncidentService {
	@Autowired
	TmpIncidentRepository incidentRepository;
	
	public Date convertStr2Date(String date) {
		Date rtnDate = null;
	    try{
	        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	        rtnDate  = dateFormat.parse(date);
	    }

	    catch(ParseException e){
	        e.printStackTrace();
	    }
	    return rtnDate;
	}
	
	private String generateHashID(Date date, String reporter, String incident, double lat, double lng, String accidentType){
        MessageDigest messageDigest = null;
        String hash = "" + date + reporter + incident + lat + lng + accidentType;

        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        messageDigest.update(hash.getBytes(), 0, hash.length());
        return new BigInteger(1, messageDigest.digest()).toString(16);
    }
	
	public IncidentDto addIncident(IncidentDto incidentDto) {
		
		IncidentDto returnIncidentDto = new IncidentDto();
		TmpIncident incident = new TmpIncident();
		
		incident.setLat(incidentDto.getLat());
		incident.setLng(incidentDto.getLng());
		incident.setIncident(incidentDto.getIncident());
		incident.setAccidentType(incidentDto.getAccidentType());
		incident.setDate(new TmpIncidentService().convertStr2Date(incidentDto.getDate()));
		incident.setReporter(incidentDto.getReporter());
		incident.setIncidentId(new TmpIncidentService().generateHashID(new TmpIncidentService().convertStr2Date(incidentDto.getDate()), incidentDto.getReporter(), incidentDto.getIncident(), incidentDto.getLat(), incidentDto.getLng(), incidentDto.getAccidentType()));
		
		returnIncidentDto.setLat(incident.getLat());
		returnIncidentDto.setLng(incident.getLng());
		returnIncidentDto.setIncident(incident.getIncident());
		returnIncidentDto.setAccidentType(incident.getAccidentType());
		returnIncidentDto.setDate(incident.getDate().toString());
		returnIncidentDto.setReporter(incident.getReporter());
		
		
		
		if(incidentRepository.findByIncidentId(incident.getIncidentId()) == null) {
			incidentRepository.save(incident);
			returnIncidentDto.setSelf("http://localhost:8081/team8/incident"+incident.getId());
			return returnIncidentDto;
		}else {
			returnIncidentDto.setSelf("Exists");
			return returnIncidentDto;
		}
	}
}
