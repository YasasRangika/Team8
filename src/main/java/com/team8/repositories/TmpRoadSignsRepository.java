package com.team8.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.team8.model.TmpRoadSigns;

public interface TmpRoadSignsRepository extends CrudRepository<TmpRoadSigns, Long> {
	@Query("select r from TmpRoadSigns r where latitude=?1 and longitude=?2 and sign=?3")
	public TmpRoadSigns findBySignInLatAndLng(double lat, double lng, String sign);
	
	@Query("select r from TmpRoadSigns r where latitude=?1 and longitude=?2")
	public TmpRoadSigns findByLatLan(double latitude, double longitude);
	
	@Query("select r from TmpRoadSigns r where r.roadSignId=?1")
	public TmpRoadSigns findByRoadSignId(String id);
}
