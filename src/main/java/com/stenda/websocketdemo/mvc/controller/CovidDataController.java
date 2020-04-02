package com.stenda.websocketdemo.mvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stenda.websocketdemo.data.CovidData;
import com.stenda.websocketdemo.service.CovidDataService;

@RestController
public class CovidDataController {
	
	@Autowired
	CovidDataService service;
	
	@GetMapping("/api/covid")
	@PreAuthorize("isAuthenticated()")
	public Page<CovidData> listAll(Pageable page){
		return service.findAll(page);
	}
	
	
}
