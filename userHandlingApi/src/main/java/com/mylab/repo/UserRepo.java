package com.mylab.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mylab.entities.UserEntity;

public interface UserRepo extends JpaRepository<UserEntity,Integer>{

	UserEntity findByEmailId(String email);

}
