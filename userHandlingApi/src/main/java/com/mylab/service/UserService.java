package com.mylab.service;

import java.util.List;

import com.mylab.dto.ActivateAccount;
import com.mylab.dto.Login;
import com.mylab.dto.RecoverPwd;
import com.mylab.dto.UserRequest;
import com.mylab.entities.UserEntity;

public interface UserService {

	public Boolean saveUser(UserRequest userRequest);

	
	public List<UserRequest> displayAllUser();
	
	public UserRequest getUserById(Integer id);
	
	public Boolean removeUser(Integer userId);
	
	public Boolean changeAccountStatus(Integer userId,String status);
	
	public String logInUser(Login login);
	
	public Boolean activateAccount(ActivateAccount requiredData);
	
	public String forgetPwd(RecoverPwd recoverData);


	 
	
}
