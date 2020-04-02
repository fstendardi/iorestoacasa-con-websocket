package com.stenda.websocketdemo.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stenda.websocketdemo.data.Comune;
import com.stenda.websocketdemo.data.Provincia;

@Service
public class ComuniService {

	@Autowired
	ObjectMapper objectMapper;
	
	List<Comune> comuni;
	
	List<Provincia> province;
	
	public List<Provincia> findProvince(){
		return province;
	}
	
	public Comune findByCap(String cap) {
		return this.comuni.stream().filter(c -> c.getCap().contains(cap)).findFirst().orElse(null);
	}
	
	@PostConstruct
	public void init() throws IOException {
		
		ClassPathResource comuniJson = new ClassPathResource("/data/comuni.json");
		
		byte[] content = new byte[(int) comuniJson.contentLength()];
		
		IOUtils.read(comuniJson.getInputStream(), content, 0, content.length);
		
		this.comuni = objectMapper.readValue(content, new TypeReference<List<Comune>>() {});
		
		this.province = new ArrayList<>(new LinkedHashSet<>(this.comuni.stream()
				.map(c -> {
					c.getProvincia().setSigla(c.getSigla());
					return c;
				})
				.map(Comune::getProvincia)				
				.sorted((p1,p2) -> p1.getNome().compareTo(p2.getNome()))
				.collect(Collectors.toList())));
		
		this.comuni = Collections.unmodifiableList(this.comuni);
		this.province = Collections.unmodifiableList(this.province);

	}
	
	
	}
