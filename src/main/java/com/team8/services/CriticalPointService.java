package com.team8.services;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team8.controllers.CriticalPointDto;
import com.team8.model.CriticalPoint;
import com.team8.repositories.CriticalPointRepository;

@Service
public class CriticalPointService {
	@Autowired
	CriticalPointRepository criticalPointRepository;
	
	private String generateHashID(double latitude, double longitude, double radius, String message, String startTime, String endTime){
        MessageDigest messageDigest = null;
        String hash = "" + latitude + longitude + radius + message + startTime + endTime;

        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        messageDigest.update(hash.getBytes(), 0, hash.length());
        return new BigInteger(1, messageDigest.digest()).toString(16);
    }
	
	public CriticalPointDto addNewCriticalPoint(CriticalPointDto criticalPointDto) {
		
		CriticalPointDto rtnCriticalPointDto = new CriticalPointDto();
		CriticalPoint criticalPoint = new CriticalPoint();
		
		criticalPoint.setLatitude(criticalPointDto.getLatitude());
		criticalPoint.setLongitude(criticalPointDto.getLongitude());
		criticalPoint.setRadius(criticalPointDto.getRadius());
		criticalPoint.setMessage(criticalPointDto.getMessage());
		criticalPoint.setStartTime(criticalPointDto.getStartTime());
		criticalPoint.setEndTime(criticalPointDto.getEndTime());
		criticalPoint.setCriticalPointId(new CriticalPointService().generateHashID(criticalPointDto.getLatitude(), criticalPointDto.getLongitude(), criticalPointDto.getRadius(), criticalPointDto.getMessage(), criticalPointDto.getStartTime(), criticalPointDto.getEndTime()));
		
		rtnCriticalPointDto.setLatitude(criticalPoint.getLatitude());
		rtnCriticalPointDto.setLongitude(criticalPoint.getLongitude());
		rtnCriticalPointDto.setRadius(criticalPoint.getRadius());
		rtnCriticalPointDto.setMessage(criticalPoint.getMessage());
		rtnCriticalPointDto.setStartTime(criticalPoint.getStartTime());
		rtnCriticalPointDto.setEndTime(criticalPoint.getEndTime());
		
		if(criticalPointRepository.findByCriticalPointId(criticalPoint.getCriticalPointId()) == null) {
			criticalPointRepository.save(criticalPoint);
			rtnCriticalPointDto.setSelf("http://localhost:8081/team8/newCritical"+criticalPoint.getId());
			return rtnCriticalPointDto;
		}else {
			rtnCriticalPointDto.setSelf("Exists");
			return rtnCriticalPointDto;
		}
	}
	
	public CriticalPointDto getCriticalPoint(CriticalPointDto criticalPointDto) {
		
		CriticalPointDto rtnCriticalPointDto = new CriticalPointDto();
		
		String id = new CriticalPointService().generateHashID(criticalPointDto.getLatitude(), criticalPointDto.getLongitude(), criticalPointDto.getRadius(), criticalPointDto.getMessage(), criticalPointDto.getStartTime(), criticalPointDto.getEndTime());
		
		CriticalPoint criticalPoint = new CriticalPoint();
		
		if(criticalPointRepository.findByCriticalPointId(id) != null) {
			
			criticalPoint = criticalPointRepository.findByCriticalPointId(id);
			
			rtnCriticalPointDto.setSelf("Exists");
			rtnCriticalPointDto.setLatitude(criticalPoint.getLatitude());
			rtnCriticalPointDto.setLongitude(criticalPoint.getLongitude());
			rtnCriticalPointDto.setRadius(criticalPoint.getRadius());
			rtnCriticalPointDto.setMessage(criticalPoint.getMessage());
			rtnCriticalPointDto.setStartTime(criticalPoint.getStartTime());
			rtnCriticalPointDto.setEndTime(criticalPoint.getEndTime());
			
			return rtnCriticalPointDto;
		}else {
			rtnCriticalPointDto.setSelf("Error");
			return rtnCriticalPointDto;
		}
	}
}
