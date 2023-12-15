package com.mylab.service;

import java.util.List;

import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;

import com.mylab.dto.ActivateAccount;
import com.mylab.dto.Login;
import com.mylab.dto.RecoverPwd;
import com.mylab.dto.UserRequest;
import com.mylab.entities.UserEntity;
import com.mylab.repo.UserRepo;

public class UserServiceImp implements UserService{
	
	@Autowired
	UserRepo userRepo;
	
	@Override
	public String saveUser(UserRequest user) {
		
		UserEntity entity=new UserEntity ();
		
		Boolean result = saveEntity(entity);
		if (result) {
			return "user is saved"; 
		}
		return "user not save";
	}
	
	

	@Override
	public List<UserRequest> displayAllUser() {
		List<UserEntity> userEntities = userRepo.findAll();
		return null;
	}

	@Override
	public UserEntity getUserById(Integer id) {
		
		UserEntity user = userRepo.getById(id);
		return user;
	}

	@Override
	public Boolean removeUser(Integer userId) {
		
		UserEntity user = getUserById(userId);
		if(user != null) {
			userRepo.deleteById(userId);
			return true;
		}
		
		return false;
	}

	@Override
	public Boolean changeAccountStatus(Integer userId, Character status) {
		UserEntity user = getUserById(userId);
		if(user != null) {
			user.setActiveSwitch(status);
			saveEntity(user);
			return true;
		}
		return false;
	}

	@Override
	public String logInUser(Login login) {
		
		String email = login.getEmailId();
		String pwd = login.getPassword();
		
		UserEntity entity = userRepo.findByEmailId(email);
		
		if(entity != null) {
			var epwd = entity.getPassword();
			if(epwd.equals(pwd)) {
				return "login is successfull";
			}
			return "password is wrong";
		}
		return "email is not found";
	}

	@Override
	public String activeAccount(ActivateAccount requiredData) {
		String email = requiredData.getEmailId();
		String oldpwd = requiredData.getOldPassword();
		String newPwd = requiredData.getNewpassword();
		String cpwd = requiredData.getConfirmPassword();
		
		if(cpwd.equals(newPwd)) {
			UserEntity entity = userRepo.findByEmailId(email);
			if(entity != null) {
				entity.setPassword(cpwd);
				entity.setActiveSwitch('Y');
				saveEntity(entity);
				return "Account activated!";
			}else {
				return "EmailId is not correct";
			}
		}else {
			return "password is not mathced";
		}
		
	}

	@Override
	public String forgetPwd(RecoverPwd recoverData) {
		String email =recoverData.getEmailId();
		String pwd = recoverData.getNewPassword();
		String cpwd = recoverData.getConfirmPassword();
		
		if(pwd.equals(cpwd)){
			
			UserEntity entity = userRepo.findByEmailId(email);
			entity.setPassword(cpwd);
			saveEntity(entity);
			return "password is changed";
		}
				
		return "password is not matched";
	}



	@Override
	public Boolean saveEntity(UserEntity userEntity) {

		UserEntity entity = userRepo.save(userEntity);

		return (entity != null) ? true : false;
	}


}
