package com.student.IdentityService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.student.IdentityService.model.Users;
import com.student.IdentityService.request.LoginRequest;
import com.student.IdentityService.request.UserCreationRequest;
import com.student.IdentityService.serviceImpl.UsersCreationImpl;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/auth")
public class IdentityController {

@Autowired 
private UsersCreationImpl usersCreationImpl;


    public IdentityController(UsersCreationImpl usersCreationImpl) {
        this.usersCreationImpl = usersCreationImpl;
    } 

   @GetMapping("/check")
    public String getIdentity() {
       System.out.println("working identity");
        return "IdentityService";
    }

    @PostMapping("/createUser")
    public String createUser(@RequestBody UserCreationRequest  userCreaionRequest) {
    String response =  usersCreationImpl.createUser(userCreaionRequest);
      return response;
    }

    @PostMapping("/login")
    public String loginUser(@RequestBody LoginRequest loginRequest) {
        String response = usersCreationImpl.LoginUser(loginRequest.email(), loginRequest.password());
        return response;
    }
    
    
    



}
