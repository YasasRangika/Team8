package com.team8.services;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team8.controllers.BlackSpotDto;
import com.team8.model.BlackSpot;
import com.team8.repositories.BlackSpotRepository;

@Service
public class BlackSpotService {
	
	@Autowired
	BlackSpotRepository blackSpotRepository;
	
	private String generateHashID(double latitude, double longitude, int con, String message){
        MessageDigest messageDigest = null;
        String hash = "" + latitude + longitude + con + message;

        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        messageDigest.update(hash.getBytes(), 0, hash.length());
        return new BigInteger(1, messageDigest.digest()).toString(16);
    }
	
	public BlackSpotDto AddNewBS(BlackSpotDto blackSpotDto) {
		
		BlackSpotDto rtnBSDto = new BlackSpotDto();
		BlackSpot blackSpot = new BlackSpot();
		
		blackSpot.setLatitude(blackSpotDto.getLatitude());
		blackSpot.setLongitude(blackSpotDto.getLongitude());
		blackSpot.setMessage(blackSpotDto.getMessage());
		blackSpot.setRadius(blackSpotDto.getRadius());
		blackSpot.setCondition(blackSpotDto.getCondition());
		blackSpot.setBlackSpotId(new BlackSpotService().generateHashID(blackSpotDto.getLatitude(), blackSpotDto.getLongitude(), blackSpotDto.getCondition(), blackSpotDto.getMessage()));
		
		rtnBSDto.setLatitude(blackSpot.getLatitude());
		rtnBSDto.setLongitude(blackSpot.getLongitude());
		rtnBSDto.setCondition(blackSpot.getCondition());
		rtnBSDto.setMessage(blackSpot.getMessage());
		rtnBSDto.setRadius(blackSpot.getRadius());
		
		
		if(blackSpotRepository.findByBlackSpotId(blackSpot.getBlackSpotId()) == null) {
			blackSpotRepository.save(blackSpot);
			rtnBSDto.setSelf("http://localhost:8081/team8/newblackspot"+blackSpot.getId());
			return rtnBSDto;
		}else {
			rtnBSDto.setSelf("Exists");
			return rtnBSDto;
		}
	}
	
	public BlackSpotDto getBlackSpot(BlackSpotDto blackSpotDto) {
		
		BlackSpotDto retrnBlackSpot = new BlackSpotDto();
		String id = new BlackSpotService().generateHashID(blackSpotDto.getLatitude(), blackSpotDto.getLongitude(), blackSpotDto.getCondition(), blackSpotDto.getMessage());
		BlackSpot blackSpot = new BlackSpot();
		
		if(blackSpotRepository.findByBlackSpotId(id) != null) {
			
			blackSpot = blackSpotRepository.findByBlackSpotId(id);
			
			retrnBlackSpot.setSelf("Exists");
			retrnBlackSpot.setLatitude(blackSpot.getLatitude());
			retrnBlackSpot.setLongitude(blackSpot.getLongitude());
			retrnBlackSpot.setCondition(blackSpot.getCondition());
			retrnBlackSpot.setMessage(blackSpot.getMessage());
			retrnBlackSpot.setRadius(blackSpot.getRadius());
			
			return retrnBlackSpot;
		}else {
			retrnBlackSpot.setSelf("Error");
			return retrnBlackSpot;
		}
	}
}
