package com.student.IdentityService.request;

import com.student.IdentityService.model.Enum.OrganizationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateOrganizationRequest(
    @NotBlank
    String name,
    @NotNull
    OrganizationType type
) {}
