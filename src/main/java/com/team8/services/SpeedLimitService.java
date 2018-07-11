package com.team8.services;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team8.controllers.SpeedLimitDto;
import com.team8.model.SpeedLimit;
import com.team8.repositories.SpeedLimitRepository;

@Service
public class SpeedLimitService {
	@Autowired
	SpeedLimitRepository speedLimitRepository;
	
	private String generateHashID(double latitude, double longitude, double speedLimit, double thresholdLimit, double radius){
        MessageDigest messageDigest = null;
        String hash = "" + latitude + longitude + speedLimit + thresholdLimit + radius;

        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        messageDigest.update(hash.getBytes(), 0, hash.length());
        return new BigInteger(1, messageDigest.digest()).toString(16);
    }
	
	public SpeedLimitDto addNewSpeedLimitPoint(SpeedLimitDto speedLimitDto) {
		
		SpeedLimitDto rtnSpeedLimitDto = new SpeedLimitDto();
		SpeedLimit speedLimit = new SpeedLimit();
		
		speedLimit.setLatitude(speedLimitDto.getLatitude());
		speedLimit.setLongitude(speedLimit.getLongitude());
		speedLimit.setSpeedLimit(speedLimitDto.getSpeedLimit());
		speedLimit.setRadius(speedLimitDto.getRadius());
		speedLimit.setThresholdLimit(speedLimitDto.getThresholdLimit());
		speedLimit.setMessage(speedLimitDto.getMessage());
		speedLimit.setSpeedlimitId(new SpeedLimitService().generateHashID(speedLimitDto.getLatitude(), speedLimitDto.getLongitude(), speedLimitDto.getSpeedLimit(), speedLimitDto.getThresholdLimit(), speedLimitDto.getRadius()));
		
		rtnSpeedLimitDto.setLatitude(speedLimit.getLatitude());
		rtnSpeedLimitDto.setLongitude(speedLimit.getLongitude());
		rtnSpeedLimitDto.setSpeedLimit(speedLimit.getSpeedLimit());
		rtnSpeedLimitDto.setRadius(speedLimit.getRadius());
		rtnSpeedLimitDto.setThresholdLimit(speedLimit.getThresholdLimit());
		rtnSpeedLimitDto.setMessage(speedLimit.getMessage());
		
		if(speedLimitRepository.findBySpeedLimitId(speedLimit.getSpeedlimitId()) == null) {
			speedLimitRepository.save(speedLimit);
			rtnSpeedLimitDto.setSelf("http://localhost:8081/team8/addSpeedLimit"+speedLimit.getId());
			return rtnSpeedLimitDto;
		}else {
			rtnSpeedLimitDto.setSelf("Exists");
			return rtnSpeedLimitDto;
		}
	}
	
	public SpeedLimitDto getSpeedLimitPoint(SpeedLimitDto speedLimitDto) {
		
		SpeedLimitDto rtnSpeedLimitDto = new SpeedLimitDto();
		String id = new SpeedLimitService().generateHashID(speedLimitDto.getLatitude(), speedLimitDto.getLongitude(), speedLimitDto.getSpeedLimit(), speedLimitDto.getThresholdLimit(), speedLimitDto.getRadius());
		SpeedLimit speedLimit = new SpeedLimit();
		
		if(speedLimitRepository.findBySpeedLimitId(id) != null) {
			speedLimit = speedLimitRepository.findBySpeedLimitId(id);
			
			rtnSpeedLimitDto.setSelf("Exists");
			rtnSpeedLimitDto.setLatitude(speedLimit.getLatitude());
			rtnSpeedLimitDto.setLongitude(speedLimit.getLongitude());
			rtnSpeedLimitDto.setSpeedLimit(speedLimit.getSpeedLimit());
			rtnSpeedLimitDto.setRadius(speedLimit.getRadius());
			rtnSpeedLimitDto.setThresholdLimit(speedLimit.getThresholdLimit());
			rtnSpeedLimitDto.setMessage(speedLimit.getMessage());
			
			return rtnSpeedLimitDto;
		}else {
			rtnSpeedLimitDto.setSelf("Error");
			return rtnSpeedLimitDto;
		}
	}
}
