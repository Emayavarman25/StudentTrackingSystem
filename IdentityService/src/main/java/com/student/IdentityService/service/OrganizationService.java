package com.student.IdentityService.service;

import java.util.List;

import com.student.IdentityService.request.CreateOrganizationRequest;
import com.student.IdentityService.response.OrganizationResponse;

public interface OrganizationService {

    OrganizationResponse createOrganization(CreateOrganizationRequest request, Long userId);

    List<OrganizationResponse> getOrganizationsForUser(Long userId);
}