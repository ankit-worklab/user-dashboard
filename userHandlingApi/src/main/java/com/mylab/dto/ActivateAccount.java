package com.mylab.dto;

import lombok.Data;

@Data
public class ActivateAccount {

	
	private String emailId;
	private String oldPassword;
	private String newpassword;
	private String confirmPassword;
	
}
