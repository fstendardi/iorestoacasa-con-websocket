package com.stenda.websocketdemo.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.stenda.websocketdemo.data.Comune;
import com.stenda.websocketdemo.data.Provincia;
import com.stenda.websocketdemo.service.ComuniService;

@RestController()
@RequestMapping("/api")
public class ComuniController {

	@Autowired
	ComuniService service;
	
	@GetMapping("/province")
	public ResponseEntity<List<Provincia>> findProvince(){
		return ResponseEntity.ok(service.findProvince());
	}
	
	@GetMapping("/comune")
	public Comune findByCap(@RequestParam String cap) {
		return this.service.findByCap(cap);
	}
}
