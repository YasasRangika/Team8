package com.team8.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.team8.model.SpeedLimit;

public interface SpeedLimitRepository extends CrudRepository<SpeedLimit, Long> {
	
	@Query("select s from SpeedLimit s where latitude=?1 and longitude=?2")
	public SpeedLimit findSpeedLimitPoint(double latitude, double longitude);
	
	@Query("select s from SpeedLimit s where s.speedlimitId=?1")
	public SpeedLimit findBySpeedLimitId(String id);
}
