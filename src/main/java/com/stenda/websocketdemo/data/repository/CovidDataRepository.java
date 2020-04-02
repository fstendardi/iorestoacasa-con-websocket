package com.stenda.websocketdemo.data.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.stenda.websocketdemo.data.CovidData;
import com.stenda.websocketdemo.data.CovidStats;

public interface CovidDataRepository extends CrudRepository<CovidData, String>{

	public CovidData findBySiglaProvinciaIgnoreCase(String siglaProvincia);
	
	@Query("SELECT new com.stenda.websocketdemo.data.CovidStats(SUM(d.totaleCasi),SUM(d.totaleGuariti),SUM(d.totaleDecessi)) FROM #{#entityName} d")
	public CovidStats getStats();
	
	public Page<CovidData> findAll(Pageable pageable);
}
