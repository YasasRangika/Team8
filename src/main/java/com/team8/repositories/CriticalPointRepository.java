package com.team8.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.team8.model.CriticalPoint;

public interface CriticalPointRepository extends CrudRepository<CriticalPoint, Long> {
	
	@Query("select c from CriticalPoint c where latitude=?1 and longitude=?2")
	public CriticalPoint findCriticalPoint(double latitude, double longitude);
	
	@Query("select c from CriticalPoint c where criticalPointId=?1")
	public CriticalPoint findByCriticalPointId(String id);
}
