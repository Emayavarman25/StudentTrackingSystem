package com.student.IdentityService.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.student.IdentityService.jwt.CustomUserDetails;
import com.student.IdentityService.response.CurrentUserResponse;

@RestController
@RequestMapping("/api/v1/users")
public class UsersController {

    @GetMapping("/me")
    public CurrentUserResponse getCurrentUser(
            @AuthenticationPrincipal CustomUserDetails currentUser) {
        return new CurrentUserResponse(
                currentUser.getUserId(),
                currentUser.getUser().getUsername(),
                currentUser.getEmail(),
                currentUser.getMobile());
    }
}
