package com.mylab.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mylab.entities.UserEntity;

public interface UserRepo extends JpaRepository<UserEntity,Integer>{

   public  UserEntity findByEmailIdAndPassword(String emailId, String password);

  public  UserEntity findByEmailId(String email);
}
