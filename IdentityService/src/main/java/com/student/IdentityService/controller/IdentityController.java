package com.student.IdentityService.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
public class IdentityController {

   @GetMapping("/check")
    public String getIdentity() {
       System.out.println("working identity");
        return "IdentityService";
    }



}
