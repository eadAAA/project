package com.microservices.user.api.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.microservices.user.api.model.dto.UserDTO;


public interface IUserService extends UserDetailsService {

	UserDTO insert(UserDTO user);

	UserDTO findByEmail(String email);

	UserDTO findById(String id);
	
}
