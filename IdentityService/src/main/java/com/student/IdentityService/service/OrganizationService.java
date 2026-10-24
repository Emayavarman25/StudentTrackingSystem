package com.student.IdentityService.service;

import java.util.List;

import com.student.IdentityService.model.Organization;
import com.student.IdentityService.request.CreateOrganizationRequest;

public interface OrganizationService {

    Organization createOrganization(CreateOrganizationRequest request, Long userId);

    List<Organization> getOrganizationsForUser(Long userId);
}