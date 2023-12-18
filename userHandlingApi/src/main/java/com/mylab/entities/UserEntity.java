package com.mylab.entities;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="UserRequest")
@Data
public class UserEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer userNo;
	private String fullName;
	private String emailId;
	private Long mobileNo;
	private Long ssn;
	private LocalDate dob;
	private String password;
	private String accountStatus;
	private String createdBy;
	private String updatedBy;
	private LocalDate createdDate;
	private LocalDate updatedDate;
}
