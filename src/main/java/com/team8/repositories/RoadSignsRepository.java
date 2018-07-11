package com.team8.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.team8.model.RoadSigns;

public interface RoadSignsRepository extends CrudRepository<RoadSigns, Long> {
	@Query("select r from RoadSigns r where latitude=?1 and longitude=?2 and sign=?3")
	public RoadSigns findBySignInLatAndLng(double lat, double lng, String sign);
	
	@Query("select r from RoadSigns r where latitude=?1 and longitude=?2")
	public RoadSigns findByLatLan(double latitude, double longitude);
	
	@Query("select r from RoadSigns r where r.roadSignId=?1")
	public RoadSigns findByRoadSignId(String id);
}
