package com.stenda.websocketdemo.websocket.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.stenda.websocketdemo.data.CovidData;
import com.stenda.websocketdemo.data.CovidStats;
import com.stenda.websocketdemo.data.SegnalazioneCaso;
import com.stenda.websocketdemo.data.Status;
import com.stenda.websocketdemo.service.CovidDataService;

@Controller
public class CovidWebsocketController {
	
	@Autowired
	CovidDataService service;
	
	//il SubscribeMapping consente di "reagire"
	//ad un subscribe inviando un messaggio di ritorno, 
	//utile per fornire il dato iniziale
	@SubscribeMapping("/public/covid") //destination che notifica i dati dei casi per ogni provincia
	public Iterable<CovidData> subscribeToCovid() {
		return service.findAll();
	}
	
	@SubscribeMapping("/public/covid.stats") //destination che notifica le statistiche globali
	public CovidStats subscribeToCovidStats() {
		return service.getStats();
	}
	
	 @MessageMapping("/public/covid/segnala") //destination usata dal client per segnalare un nuovo caso
	 @SendTo("/app/public/covid") //con questa annotazione il risultato del metodo viene inviato alla destination indicata
	 public Iterable<CovidData> submitCase(@Payload SegnalazioneCaso segnalazione) throws Exception {
		 
		 return Arrays.asList(service.segnalaCaso(segnalazione));
		 
	 }

	
}
