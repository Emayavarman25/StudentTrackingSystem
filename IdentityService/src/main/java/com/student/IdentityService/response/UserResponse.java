package com.student.IdentityService.response;

import java.util.List;

public record UserResponse(
        Long id,
        String username,
        String email,
        String mobile,
        List<String> roles) {
}
