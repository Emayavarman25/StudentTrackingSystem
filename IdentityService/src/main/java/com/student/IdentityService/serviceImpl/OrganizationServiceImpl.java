package com.student.IdentityService.serviceImpl;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.student.IdentityService.model.Enum.MembershipStatus;
import com.student.IdentityService.model.Enum.OrganizationStatus;
import com.student.IdentityService.model.Organization;
import com.student.IdentityService.model.OrganizationMembership;
import com.student.IdentityService.model.MembershipRole;
import com.student.IdentityService.model.Role;
import com.student.IdentityService.model.Users;
import com.student.IdentityService.repository.MembershipRoleRepository;
import com.student.IdentityService.repository.OrganizationMembershipRepository;
import com.student.IdentityService.repository.OrganizationRepository;
import com.student.IdentityService.repository.RoleRepo;
import com.student.IdentityService.repository.UsersRepo;
import com.student.IdentityService.request.CreateOrganizationRequest;
import com.student.IdentityService.response.OrganizationResponse;
import com.student.IdentityService.service.OrganizationService;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final OrganizationMembershipRepository membershipRepository;
    private final MembershipRoleRepository membershipRoleRepository;
    private final RoleRepo roleRepository;
    private final UsersRepo usersRepository;

    public OrganizationServiceImpl(
            OrganizationRepository organizationRepository,
            OrganizationMembershipRepository membershipRepository,
            MembershipRoleRepository membershipRoleRepository,
            RoleRepo roleRepository,
            UsersRepo usersRepository) {
        this.organizationRepository = organizationRepository;
        this.membershipRepository = membershipRepository;
        this.membershipRoleRepository = membershipRoleRepository;
        this.roleRepository = roleRepository;
        this.usersRepository = usersRepository;
    }

    @Override
    @Transactional
    public OrganizationResponse createOrganization(CreateOrganizationRequest request, Long userId) {
        String organizationName = request.name().trim();
        if (organizationRepository.existsByName(organizationName)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Organization with the same name already exists");
        }

        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        Organization organization = new Organization();
        organization.setName(organizationName);
        organization.setType(request.type());
        organization.setStatus(OrganizationStatus.ACTIVE);
        Organization savedOrganization = organizationRepository.save(organization);

        OrganizationMembership membership = new OrganizationMembership();
        membership.setUser(user);
        membership.setOrganization(savedOrganization);
        membership.setStatus(MembershipStatus.ACTIVE);
        OrganizationMembership savedMembership = membershipRepository.save(membership);

        Role organizationAdminRole = roleRepository.findByName("ORG_ADMIN")
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "Organization administrator role is not initialized"));
        membershipRoleRepository.save(new MembershipRole(savedMembership, organizationAdminRole));

        return toResponse(savedOrganization);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrganizationResponse> getOrganizationsForUser(Long userId) {
        return membershipRepository
                .findAllByUser_IdAndStatus(userId, MembershipStatus.ACTIVE)
                .stream()
                .map(OrganizationMembership::getOrganization)
                .map(this::toResponse)
                .toList();
    }

    private OrganizationResponse toResponse(Organization organization) {
        return new OrganizationResponse(
                organization.getId(),
                organization.getName(),
                organization.getStatus(),
                organization.getType());
    }
}
