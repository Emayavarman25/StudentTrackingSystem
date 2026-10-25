package com.student.IdentityService.response;

import java.time.Instant;

public record MembershipRoleResponse(
        Long membershipRoleId,
        Long membershipId,
        Long organizationId,
        Long roleId,
        String roleName,
        Instant assignedAt) {
}
