package com.dev.service;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.dev.entities.User;
import com.dev.requests.PasswordRequest;
import com.dev.responses.LoginResponse;
import com.dev.responses.MessageResponse;

public interface UserService extends UserDetailsService{
	
	public MessageResponse save(User user);

	public MessageResponse update(User user);

	public MessageResponse delete(Integer id);

	public List<User> findAll();

	public User findById(Integer id);
	
	public MessageResponse changePassword(PasswordRequest passwordRequest);
	
	public LoginResponse authentication(User user);

	
}
