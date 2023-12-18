package com.mylab.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.mylab.utils.EmailUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.mylab.dto.ActivateAccount;
import com.mylab.dto.Login;
import com.mylab.dto.RecoverPwd;
import com.mylab.dto.UserRequest;
import com.mylab.entities.UserEntity;
import com.mylab.repo.UserRepo;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserService{
	
	@Autowired
	UserRepo userRepo;
	
	@Override
	public Boolean saveUser(UserRequest user) {
		
		UserEntity entity=new UserEntity ();
        String tempPassword = getRandomPassword();
		BeanUtils.copyProperties(user,entity);
		entity.setAccountStatus("In-Active");
		entity.setPassword(tempPassword);
        String sub = "Your Account Registration Success !";
		String fileName="REG_EMAIL_BODY.txt";
		String url="";
        String body = readEmailBody(user.getFullName(),tempPassword,fileName,url);
        try{
            userRepo.save(entity);
            EmailUtils email = new EmailUtils();
            email.sendEmail(user.getEmailId(),sub,body);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;

        }

	}
	
	private String getRandomPassword(){
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~`!@#$%^&*()-_=+[{]}\\|;:'\",<.>/?";
		String pwd = RandomStringUtils.random( 6, characters );
		System.out.println( pwd );
		return pwd;
	}

	@Override
	public List<UserRequest> displayAllUser() {
		List<UserEntity> userEntities = userRepo.findAll();
		List<UserRequest> users= new ArrayList<>();
		userEntities.forEach(entity->{
			UserRequest requestUser = new UserRequest();
			BeanUtils.copyProperties(entity,requestUser);
			users.add(requestUser);
		});
		return users;
	}

	@Override
	public UserRequest getUserById(Integer id) {
		
		Optional<UserEntity> entity = userRepo.findById(id);
		if(entity.isPresent()){
			UserRequest request = new UserRequest();
			BeanUtils.copyProperties(entity,request);
			return request;
		}
		return null;
	}

	@Override
	public Boolean removeUser(Integer userId) {
		
		Optional<UserEntity> user =userRepo.findById(userId);
		if(user.isPresent()) {
			userRepo.deleteById(userId);
			return true;
		}
		return false;
	}

	@Override
	public Boolean changeAccountStatus(Integer userId, String status) {
		Optional<UserEntity> findById = userRepo.findById(userId);
		if (findById.isPresent()) {
			UserEntity entity =findById.get();
			entity.setAccountStatus(status);
			 userRepo.save(entity);
			return true;
		}
		return false;
	}


	@Override
	public String logInUser(Login login) {
		UserEntity entity = userRepo.findByEmailIdAndPassword(login.getEmailId(),login.getPassword());
		
		if(entity == null){
			return  "invalid credentials";
		}
		else {
			if(entity.getAccountStatus().equals("Active")){
				return  "Login success";
			}
			return "Account not activated";
		}
	}

	@Override
	public Boolean activateAccount(ActivateAccount requiredData) {
		String email = requiredData.getEmailId();
		String oldpwd = requiredData.getOldPassword();
		String newPwd = requiredData.getNewpassword();
//		String cpwd = requiredData.getConfirmPassword();

		UserEntity entity = new UserEntity();
		entity.setEmailId(email);
		entity.setPassword(oldpwd);

		Example<UserEntity> entityExample = Example.of(entity);
		List<UserEntity> entities = userRepo.findAll(entityExample);
		if(entities.isEmpty()){
			return false;
		}else {
			UserEntity entity1 = userRepo.findAll().get(0);
			entity1.setPassword(newPwd);
			entity1.setAccountStatus("Active");
			userRepo.save(entity1);
			return true;
		}
	}

	@Override
	public String forgetPwd(RecoverPwd recoverData) {
		String email = recoverData.getEmailId();
		String pwd = recoverData.getNewPassword();
		String cpwd = recoverData.getConfirmPassword();

		UserEntity entity = userRepo.findByEmailId(email);
		if(entity == null){
			return "invalid EmailId !";
		}
		EmailUtils utils = new EmailUtils();
		String sub = "Forget password";
		String url ="";
		String fileName = "PWD_RECOVER_BODY";
				entity.setPassword(cpwd);
				userRepo.save(entity);
				String body=readEmailBody(entity.getFullName(),pwd,fileName,url);
				boolean isSend=utils.sendEmail(email,sub,body);
		if(isSend){
			return "password send to your registered EmailId !";

			}
		return  "password updation failed !";

	}

    private String readEmailBody(String fullName , String pwd, String fileName, String url){

       String msgBody=null;
        StringBuilder builder = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
           String value= reader.readLine();
            while(value != null){
                builder.append(value);
                value=reader.readLine();
            }
            msgBody= builder.toString();
            msgBody.replace("{fullName}",fullName);
            msgBody.replace("{password}", pwd);
            msgBody.replace("{url}",url);


        }catch(Exception e ){
            e.printStackTrace();
        }
        return msgBody;
    }

}
