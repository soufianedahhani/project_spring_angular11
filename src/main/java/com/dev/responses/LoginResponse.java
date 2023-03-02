package com.dev.responses;

import com.dev.entities.User;

import lombok.Data;

@Data
public class LoginResponse {
	
	private String token;
	private User user;

}
