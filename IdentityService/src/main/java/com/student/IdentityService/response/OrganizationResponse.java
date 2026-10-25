package com.student.IdentityService.response;

import com.student.IdentityService.model.Enum.OrganizationStatus;
import com.student.IdentityService.model.Enum.OrganizationType;

public record OrganizationResponse(
        Long id,
        String name,
        OrganizationStatus status,
        OrganizationType type) {
}
