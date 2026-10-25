package com.student.IdentityService.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.student.IdentityService.jwt.CustomUserDetails;
import com.student.IdentityService.request.AssignMembershipRoleRequest;
import com.student.IdentityService.response.MembershipRoleResponse;
import com.student.IdentityService.service.MembershipRoleService;

import jakarta.validation.Valid;

@RestController
@Validated
@RequestMapping("/api/v1/organizations/{organizationId}/memberships/{membershipId}/roles")
public class MembershipRoleController {

    private final MembershipRoleService membershipRoleService;

    public MembershipRoleController(MembershipRoleService membershipRoleService) {
        this.membershipRoleService = membershipRoleService;
    }

    @PostMapping
    public ResponseEntity<List<MembershipRoleResponse>> assignRoles(
            @PathVariable Long organizationId,
            @PathVariable Long membershipId,
            @Valid @RequestBody AssignMembershipRoleRequest request,
            @AuthenticationPrincipal CustomUserDetails currentUser) {
        List<MembershipRoleResponse> response = membershipRoleService.assignRoles(
                organizationId,
                membershipId,
                currentUser.getUserId(),
                request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<MembershipRoleResponse>> getRoles(
            @PathVariable Long organizationId,
            @PathVariable Long membershipId,
            @AuthenticationPrincipal CustomUserDetails currentUser) {
        return ResponseEntity.ok(membershipRoleService.getRoles(
                organizationId,
                membershipId,
                currentUser.getUserId()));
    }
}
