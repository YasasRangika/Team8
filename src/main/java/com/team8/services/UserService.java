package com.team8.services;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team8.controllers.UserDto;
import com.team8.model.User;
import com.team8.repositories.UserRepository;

@Service
public class UserService {
	@Autowired
	UserRepository userRepository;
	
	public Date convertStr2Date(String date) {
		Date rtnDate = null;
	    try{
	        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	        rtnDate  = dateFormat.parse(date);
	    }

	    catch(ParseException e){
	        e.printStackTrace();
	    }
	    return rtnDate;
	}
	
	private String generateHashID(String username, int phnnumber, String email, String nic){
        MessageDigest messageDigest = null;
        String hash = "" + username + phnnumber + email + nic;

        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        messageDigest.update(hash.getBytes(), 0, hash.length());
        return new BigInteger(1, messageDigest.digest()).toString(16);
    }
	
	public UserDto addUser(UserDto userDto) {
		
		UserDto returnUserDto = new UserDto();
		User user = new User();
		
		user.setUsername(userDto.getUsername());
		user.setEmail(userDto.getEmail());
		user.setPhonenumber(userDto.getPhonenumber());
		user.setPassword(userDto.getPassword());
		user.setLicenseUrl(userDto.getLicenseUrl());
		user.setUserId(new UserService().generateHashID(userDto.getUsername(), userDto.getPhonenumber(), userDto.getEmail(), userDto.getNic()));
		user.setDob(new UserService().convertStr2Date(userDto.getDob()));
		user.setDateOfIssueLicense(new UserService().convertStr2Date(userDto.getDateOfIssueLicense()));
		user.setDateOfExpireLicense(new UserService().convertStr2Date(userDto.getDateOfExpireLicense()));
		user.setNic(userDto.getNic());
		user.setAddress(userDto.getAddress());
		user.setImageOfDriverUrl(userDto.getImageOfDriverUrl());
		
		returnUserDto.setUserID(user.getUserId());
		returnUserDto.setUsername(user.getUsername());
		returnUserDto.setEmail(user.getEmail());
		returnUserDto.setPhonenumber(user.getPhonenumber());
		returnUserDto.setNic(user.getNic());
		
		if(userRepository.getUserByUserId(new UserService().generateHashID(user.getUsername(), user.getPhonenumber(), user.getEmail(), user.getNic())) == null) {
			returnUserDto.setSelf("http://localhost:8081/team8/user"+user.getId());
			userRepository.save(user);
			return returnUserDto;
		}else {
			returnUserDto.setSelf("Exists");
			return returnUserDto;
		}
	}
	
	public UserDto getUser(UserDto userDto) {
		UserDto returnUserDto = new UserDto();
		User u = new User();
		String id = new UserService().generateHashID(userDto.getUsername(), userDto.getPhonenumber(), userDto.getEmail(), userDto.getNic());
		
		if(userRepository.getUserByUserId(id) != null) {
			u = userRepository.getUserByUserId(id);
			returnUserDto.setSelf("Exists");
			returnUserDto.setUsername(u.getUsername());
			returnUserDto.setEmail(u.getEmail());
			returnUserDto.setPhonenumber(u.getPhonenumber());
			returnUserDto.setLicenseUrl(u.getLicenseUrl());
			return returnUserDto;
		}else {
			returnUserDto.setSelf("Error");
			return returnUserDto;
		}
	}
}
