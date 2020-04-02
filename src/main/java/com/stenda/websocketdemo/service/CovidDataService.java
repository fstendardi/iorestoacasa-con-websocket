package com.stenda.websocketdemo.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.stenda.websocketdemo.data.CovidData;
import com.stenda.websocketdemo.data.CovidStats;
import com.stenda.websocketdemo.data.SegnalazioneCaso;
import com.stenda.websocketdemo.data.Status;
import com.stenda.websocketdemo.data.User;
import com.stenda.websocketdemo.data.repository.CovidDataRepository;

@Service
public class CovidDataService {

	@Autowired
	CovidDataRepository repository;

	@Autowired
	ObjectMapper mapper;

	@Autowired
	SimpMessagingTemplate messaging;
	
	@Autowired
	UserService userService;

	public Iterable<CovidData> findAll() {
		return repository.findAll();
	}
	
	public Page<CovidData> findAll(Pageable pageable){
		return repository.findAll(pageable);
	}

	@PostConstruct
	public void init() throws IOException {

		ensureInitialData();

	}

	private void ensureInitialData() throws IOException {
		if (repository.count() == 0) {

			ObjectMapper om = mapper.copy();

			om.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);

			ClassPathResource dataJSON = new ClassPathResource("/data/covid-data.json");

			byte[] content = new byte[(int) dataJSON.contentLength()];

			IOUtils.read(dataJSON.getInputStream(), content, 0, content.length);

			List<CovidData> data = om.readValue(content, new TypeReference<List<CovidData>>() {
			});

			repository.saveAll(data.stream().filter(d -> !StringUtils.isEmpty(d.getSiglaProvincia())).map(d -> {
				d.setDataCreazione(new Date());
				d.setCreatoDa("admin");
				d.setTotaleDecessi((int) (d.getTotaleCasi() * 0.10));
				d.setTotaleGuariti((int) (d.getTotaleCasi() * 0.16));
				return d;
			})

					.collect(Collectors.toList()));
		}
	}

	public CovidData segnalaCaso(SegnalazioneCaso segnalazione) {
		CovidData data = repository.findBySiglaProvinciaIgnoreCase(segnalazione.getProvincia());

		Assert.notNull(data, "Provincia non trovata");
		
		if (segnalazione.getStatus().equals(Status.GUARITO)) {
			data.setTotaleGuariti(data.getTotaleGuariti() + 1);
			data.setTotaleCasi(data.getTotaleCasi() - 1);
		} else {
			data.setTotaleCasi(data.getTotaleCasi() + 1);
		}
		save(data);
		
		messaging.convertAndSend("/app/public/covid.stats", getStats());
		
		Iterable<User> users = userService.findByProvincia(data.getSiglaProvincia());
		
		users.forEach(u -> {
			messaging.convertAndSendToUser(u.getUsername(), "/admin/covid.segnalazioni", segnalazione);	
		});
		
		return data;
	}

	private void save(CovidData data) {
		repository.save(data);
	}

	public CovidStats getStats() {
		return repository.getStats();
	}

}
