package com.student.IdentityService.response;

public record LoginResponse(
        String accessToken,
        String tokenType,
        long expiresIn) {
}
