package com.mylab.dto;

import java.time.LocalDate;

import lombok.Data;
@Data
public class UserRequest {

	private String fullName;
	private String emailId;
	private Long mobileNo;
	private Long ssn;
	private LocalDate dob;
}
