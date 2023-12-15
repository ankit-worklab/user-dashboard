package com.mylab.dto;

import lombok.Data;

@Data
public class RecoverPwd {

	private String emailId;
	private String newPassword;
	private String confirmPassword;
}
