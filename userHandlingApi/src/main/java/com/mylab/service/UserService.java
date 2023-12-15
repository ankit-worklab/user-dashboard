package com.mylab.service;

import java.util.List;

import com.mylab.dto.ActivateAccount;
import com.mylab.dto.Login;
import com.mylab.dto.RecoverPwd;
import com.mylab.dto.UserRequest;
import com.mylab.entities.UserEntity;

public interface UserService {

	public String saveUser(UserRequest userRequest);
	
	public Boolean saveEntity(UserEntity userEntity);
	
	public List<UserRequest> displayAllUser();
	
	public UserEntity getUserById(Integer id);
	
	public String removeUser(Integer userId);
	
	public Boolean changeAccountStatus(Integer userId,Character status);
	
	public String logInUser(Login login);
	
	public String activeAccount(ActivateAccount requiredData);
	
	public String forgetPwd(RecoverPwd recoverData);


	 
	
}
