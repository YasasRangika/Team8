package com.team8.services;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team8.controllers.RoadSignsDto;
import com.team8.model.TmpRoadSigns;
import com.team8.repositories.TmpRoadSignsRepository;
@Service
public class TmpRoadSignsService {
	
	@Autowired
	private TmpRoadSignsRepository roadSignsRepository;
	
	private String generateHashID(double latitude, double longitude, double radius, String sign){
        MessageDigest messageDigest = null;
        String hash = "" + latitude + longitude + radius + sign;

        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        messageDigest.update(hash.getBytes(), 0, hash.length());
        return new BigInteger(1, messageDigest.digest()).toString(16);
    }
	
	public RoadSignsDto saveNewSign(RoadSignsDto roadSignsDto) {
		
		RoadSignsDto returnRoadSignsDto = new RoadSignsDto();
		
		TmpRoadSigns roadSigns = new TmpRoadSigns();
		roadSigns.setLatitude(roadSignsDto.getLatitude());
		roadSigns.setLongitude(roadSignsDto.getLongitude());
		roadSigns.setSign(roadSignsDto.getSign());
		roadSigns.setRadius(roadSignsDto.getRadius());
		roadSigns.setMessage(roadSignsDto.getMessage());
		roadSigns.setRoadSignId(new TmpRoadSignsService().generateHashID(roadSignsDto.getLatitude(), roadSignsDto.getLongitude(), roadSignsDto.getRadius(), roadSignsDto.getSign()));
		
		returnRoadSignsDto.setLatitude(roadSigns.getLatitude());
		returnRoadSignsDto.setLongitude(roadSigns.getLongitude());
		returnRoadSignsDto.setSign(roadSigns.getSign());
		returnRoadSignsDto.setRadius(roadSigns.getRadius());
		returnRoadSignsDto.setMessage(roadSigns.getMessage());
		
		if(roadSignsRepository.findByRoadSignId(roadSigns.getRoadSignId())==null) {
			returnRoadSignsDto.setSelf("http://localhost:8081/team8/sign"+roadSigns.getId());
			roadSignsRepository.save(roadSigns);
			
		}else {
			returnRoadSignsDto.setSelf("Exists");
			
		}
		return returnRoadSignsDto;
	}

}
