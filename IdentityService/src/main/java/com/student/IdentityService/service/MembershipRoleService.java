package com.student.IdentityService.service;

import java.util.List;

import com.student.IdentityService.request.AssignMembershipRoleRequest;
import com.student.IdentityService.response.MembershipRoleResponse;

public interface MembershipRoleService {

    List<MembershipRoleResponse> assignRoles(
            Long organizationId,
            Long membershipId,
            Long requestingUserId,
            AssignMembershipRoleRequest request);

    List<MembershipRoleResponse> getRoles(
            Long organizationId,
            Long membershipId,
            Long requestingUserId);
}
