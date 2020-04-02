package com.stenda.websocketdemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.stenda.websocketdemo.data.User;
import com.stenda.websocketdemo.data.repository.UserRepository;

@Component
public class CustomUserDetailService implements UserDetailsService{
	
	 @Autowired
	 
	 UserRepository userRepository;
	 

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findById(username).orElse(null);
		if (user == null) {
			throw new UsernameNotFoundException("User not found");
		}
		return new CustomPrincipal(user);
	}
	
	
}
