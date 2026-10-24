package com.student.IdentityService.service;

import com.student.IdentityService.model.Users;

public interface UsersCreations {

    Users signup(Users user);

    Users signIn(Users input);


}
