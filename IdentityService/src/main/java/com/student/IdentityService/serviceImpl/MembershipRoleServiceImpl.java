package com.student.IdentityService.serviceImpl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.student.IdentityService.model.Enum.MembershipStatus;
import com.student.IdentityService.model.MembershipRole;
import com.student.IdentityService.model.OrganizationMembership;
import com.student.IdentityService.model.Role;
import com.student.IdentityService.repository.MembershipRoleRepository;
import com.student.IdentityService.repository.OrganizationMembershipRepository;
import com.student.IdentityService.repository.RoleRepo;
import com.student.IdentityService.request.AssignMembershipRoleRequest;
import com.student.IdentityService.response.MembershipRoleResponse;
import com.student.IdentityService.service.MembershipRoleService;

@Service
public class MembershipRoleServiceImpl implements MembershipRoleService {

    private static final String ORGANIZATION_ADMIN_ROLE = "ORG_ADMIN";

    private final OrganizationMembershipRepository membershipRepository;
    private final MembershipRoleRepository membershipRoleRepository;
    private final RoleRepo roleRepository;

    public MembershipRoleServiceImpl(
            OrganizationMembershipRepository membershipRepository,
            MembershipRoleRepository membershipRoleRepository,
            RoleRepo roleRepository) {
        this.membershipRepository = membershipRepository;
        this.membershipRoleRepository = membershipRoleRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public List<MembershipRoleResponse> assignRoles(
            Long organizationId,
            Long membershipId,
            Long requestingUserId,
            AssignMembershipRoleRequest request) {
        requireOrganizationAdmin(organizationId, requestingUserId);

        OrganizationMembership membership = membershipRepository
                .findByIdAndOrganization_Id(membershipId, organizationId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Membership not found in this organization"));

        if (membership.getStatus() != MembershipStatus.ACTIVE) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Roles can only be assigned to active memberships");
        }

        Set<Long> uniqueRoleIds = new HashSet<>(request.roleIds());
        if (uniqueRoleIds.size() != request.roleIds().size()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "The request contains duplicate role IDs");
        }

        List<Role> roles = roleRepository.findAllById(uniqueRoleIds);
        if (roles.size() != uniqueRoleIds.size()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "One or more roles were not found");
        }

        if (roles.stream().anyMatch(role -> role.getName().startsWith("ROLE_")
                || role.getName().startsWith("PLATFORM_"))) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Platform roles cannot be assigned to organization memberships");
        }

        if (roles.stream().anyMatch(role -> membershipRoleRepository
                .existsByMembership_IdAndRole_Id(membershipId, role.getId()))) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "One or more roles are already assigned to the membership");
        }

        return roles.stream()
                .map(role -> membershipRoleRepository.save(new MembershipRole(membership, role)))
                .map(membershipRole -> toResponse(membershipRole, organizationId))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<MembershipRoleResponse> getRoles(
            Long organizationId,
            Long membershipId,
            Long requestingUserId) {
        OrganizationMembership membership = membershipRepository
                .findByIdAndOrganization_Id(membershipId, organizationId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Membership not found in this organization"));

        boolean isMember = membership.getUser().getId().equals(requestingUserId)
                && membership.getStatus() == MembershipStatus.ACTIVE;
        if (!isMember && !isOrganizationAdmin(organizationId, requestingUserId)) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "You are not authorized to view this membership's roles");
        }

        return membershipRoleRepository.findAllByMembership_Id(membershipId)
                .stream()
                .map(membershipRole -> toResponse(membershipRole, organizationId))
                .toList();
    }

    private void requireOrganizationAdmin(Long organizationId, Long requestingUserId) {
        if (!isOrganizationAdmin(organizationId, requestingUserId)) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "An active organization administrator membership is required");
        }
    }

    private boolean isOrganizationAdmin(Long organizationId, Long requestingUserId) {
        return membershipRepository
                .existsByUser_IdAndOrganization_IdAndStatusAndMembershipRoles_Role_Name(
                        requestingUserId,
                        organizationId,
                        MembershipStatus.ACTIVE,
                        ORGANIZATION_ADMIN_ROLE);
    }

    private MembershipRoleResponse toResponse(
            MembershipRole membershipRole,
            Long organizationId) {
        return new MembershipRoleResponse(
                membershipRole.getId(),
                membershipRole.getMembership().getId(),
                organizationId,
                membershipRole.getRole().getId(),
                membershipRole.getRole().getName(),
                membershipRole.getAssignedAt());
    }
}
