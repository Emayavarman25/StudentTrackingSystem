package com.student.IdentityService.request;

import com.fasterxml.jackson.annotation.JsonAlias;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @JsonAlias({"email", "mobile"})
        @NotBlank String identifier,
        @NotBlank String password) {
}
