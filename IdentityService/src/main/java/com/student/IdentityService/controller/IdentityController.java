package com.student.IdentityService.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.student.IdentityService.request.LoginRequest;
import com.student.IdentityService.request.UserCreationRequest;
import com.student.IdentityService.response.LoginResponse;
import com.student.IdentityService.response.UserResponse;
import com.student.IdentityService.serviceImpl.UsersCreationImpl;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/v1/auth")
public class IdentityController {

    private final UsersCreationImpl usersCreationImpl;

    public IdentityController(UsersCreationImpl usersCreationImpl) {
        this.usersCreationImpl = usersCreationImpl;
    }

   @GetMapping("/check")
    public String getIdentity() {
       System.out.println("working identity");
        return "IdentityService";
    }

    @PostMapping({"/createUser", "/register"})
    public ResponseEntity<UserResponse> createUser(
            @Valid @RequestBody UserCreationRequest userCreationRequest) {
        UserResponse response = usersCreationImpl.createUser(userCreationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@Valid @RequestBody LoginRequest loginRequest) {
        LoginResponse response = usersCreationImpl.LoginUser(
                loginRequest.identifier(),
                loginRequest.password());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    
    
    



}
