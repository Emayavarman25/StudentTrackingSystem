package com.student.IdentityService.request;

import java.util.Set;

public record UserCreationRequest(String firstName, String lastName, String email, String password,String mobileNumber,Set<String> roles) {

}
