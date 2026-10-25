package com.student.IdentityService.response;

public record CurrentUserResponse(
        Long id,
        String username,
        String email,
        String mobile) {
}
