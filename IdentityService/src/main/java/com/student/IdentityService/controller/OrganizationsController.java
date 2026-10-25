package com.student.IdentityService.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.student.IdentityService.jwt.CustomUserDetails;
import com.student.IdentityService.request.CreateOrganizationRequest;
import com.student.IdentityService.response.OrganizationResponse;
import com.student.IdentityService.service.OrganizationService;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/v1/organizations")
public class OrganizationsController {

    private final OrganizationService organizationService;

    public OrganizationsController(
            OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @PostMapping("/createOrganization")
    public ResponseEntity<OrganizationResponse> createOrganization(
            @Valid @RequestBody CreateOrganizationRequest request,
            @AuthenticationPrincipal CustomUserDetails currentUser) {
        OrganizationResponse organization = organizationService.createOrganization(request, currentUser.getUserId());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(organization);
    }

    @GetMapping("/getorganizations")
    public ResponseEntity<List<OrganizationResponse>> getOrganizations(
            @AuthenticationPrincipal CustomUserDetails currentUser) {
        return ResponseEntity.ok(organizationService.getOrganizationsForUser(currentUser.getUserId()));
    }
}