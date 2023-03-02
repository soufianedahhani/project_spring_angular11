package com.dev.requests;

import lombok.Data;

@Data
public class PasswordRequest {
	
	private Integer id;
	private String oldPassword;
	private String newPassword;
	

}
