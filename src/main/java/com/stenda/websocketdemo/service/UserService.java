package com.stenda.websocketdemo.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.stenda.websocketdemo.data.User;
import com.stenda.websocketdemo.data.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository repository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	public Iterable<User> findByProvincia(String provincia){
		return repository.findByProvincia(provincia);
	}
	
	@PostConstruct
	public void init() {
		if (repository.count() == 0) {
			User user = new User();
			
			user.setUsername("user1");
			user.setPassword(passwordEncoder.encode("password"));
			user.setNome("Mario");
			user.setCognome("Rossi");
			user.setProvincia("AR");
		
			repository.save(user);
			
			user = new User();
			
			user.setUsername("user2");
			user.setPassword(passwordEncoder.encode("password"));
			user.setNome("Fabio");
			user.setCognome("Verdi");
			user.setProvincia("FI");
		
			repository.save(user);
		}
	}
}
