package com.team8.helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Str2Date {
	public Date convertStr2Date(String date) {
		Date rtnDate = null;
	    try{
	        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	        rtnDate  = dateFormat.parse(date);
	        System.out.println(rtnDate);
	    }

	    catch(ParseException e){
	        e.printStackTrace();
	    }
	    return rtnDate;
	}
}
