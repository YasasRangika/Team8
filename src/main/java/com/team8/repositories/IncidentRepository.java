package com.team8.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.team8.model.Incident;

public interface IncidentRepository extends CrudRepository<Incident, Long> {
	
	@Query("select i from Incident i where lat=?1 and lng=?2 and accidentType=?3 and date=?4 and reporter=?5")
	public Incident findIncident(double lat, double lng, String accidentType, String date, String reporter);
	
	@Query("select i from Incident i where i.lat=?1 and i.lng=?2")
	public Incident findByLatLan(double lat, double lng) ;
	
	@Query("select i from Incident i where i.incidentId=?1")
	public Incident findByIncidentId(String incidentId);
}
