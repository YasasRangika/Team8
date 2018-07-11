package com.team8.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.team8.model.BlackSpot;

public interface BlackSpotRepository extends CrudRepository<BlackSpot, Long> {
	
	@Query("select b from BlackSpot b where b.latitude=?1 and b.longitude=?2")
	public BlackSpot findBlackSpot(double latitude, double longitude);
	
	@Query("select b from BlackSpot b where b.blackSpotId=?1")
	public BlackSpot findByBlackSpotId(String id);
}
