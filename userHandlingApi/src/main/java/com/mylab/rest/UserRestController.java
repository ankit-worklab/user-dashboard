package com.mylab.rest;

import com.mylab.dto.ActivateAccount;
import com.mylab.dto.Login;
import com.mylab.dto.RecoverPwd;
import com.mylab.dto.UserRequest;
import com.mylab.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResponseErrorHandler;

import java.util.List;

@RestController
public class UserRestController {
    @Autowired
    UserService service;
    @PostMapping("/save")
    public ResponseEntity<String> registerUser(@RequestBody UserRequest user){
        boolean isSaved = service.saveUser(user);
        String output=null;
        if(isSaved){
            output = "User Registration successfull";
            return new ResponseEntity<>(output, HttpStatus.OK);

        }
        output="Registration failed ";

        return new ResponseEntity<>(output, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/activateAccount")
    public ResponseEntity<String> activateUser(@RequestBody ActivateAccount account){
        boolean isActivated = service.activateAccount(account);
        String output=null;
        if(isActivated){
            output = "Account activation Success!";
            return new ResponseEntity<>(output, HttpStatus.OK);

        }
        output="Acivation failed";
        return new ResponseEntity<>(output, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserRequest>> getAllUsers(){
        List<UserRequest> users = service.displayAllUser();
        return new ResponseEntity<>(users, HttpStatus.OK);

    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<UserRequest> getUserById(@PathVariable Integer userId){
        UserRequest user = service.getUserById(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Integer userId){
        boolean isDeleted = service.removeUser(userId);
        String output=null;
        if(isDeleted){
            output="Account removed successfully";
            return new ResponseEntity<>(output, HttpStatus.OK);
        }
        output="Operation Failed";
        return new ResponseEntity<>(output, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @GetMapping("/changeStatus/{userId}/{status}")
    public ResponseEntity<String> changeAccountStatus(@PathVariable Integer userId, @PathVariable String status){
        boolean isChanged = service.changeAccountStatus(userId,status);
        String output="";
        if(isChanged){
            output ="Account status changed Successfully";
            return new ResponseEntity<>(output, HttpStatus.OK);

        }
        output="Operation Failed try again";
        return new ResponseEntity<>(output, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody Login login){
        String output = service.logInUser(login);
        return new ResponseEntity<>(output, HttpStatus.OK);

    }

    @PutMapping("/forget")
    public ResponseEntity<String> forgetPwd(@RequestBody RecoverPwd recover){
        String output = service.forgetPwd(recover);
        return new ResponseEntity<>(output, HttpStatus.OK);

    }






























}
