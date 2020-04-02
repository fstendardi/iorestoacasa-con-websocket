package com.stenda.websocketdemo.data.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.stenda.websocketdemo.data.User;

public interface UserRepository extends CrudRepository<User, String>{

	public User findByUsername(String username);
	
	public List<User> findByProvincia(String provincia);
}
