package com.student.IdentityService.service;

import com.student.IdentityService.model.Users;

public interface UsersCreations {

    Users signUp(Users user);
    Users signIn(Users user);

}
