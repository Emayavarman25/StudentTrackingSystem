package com.student.IdentityService.request;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record AssignMembershipRoleRequest(
        @NotEmpty @Size(max = 20) List<@NotNull @Positive Long> roleIds) {
}
