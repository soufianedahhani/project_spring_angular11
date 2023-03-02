package com.dev.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dev.entities.User;
import com.dev.responses.LoginResponse;
import com.dev.service.UserService;

@RestController
@RequestMapping("/login")
@CrossOrigin("*")
public class LoginController { 
	
	@Autowired
	private UserService userservice;
	
	
	@PostMapping
	public LoginResponse login(@RequestBody User user) {
		
		return userservice.authentication(user);
	}
	
	
	
	

}
