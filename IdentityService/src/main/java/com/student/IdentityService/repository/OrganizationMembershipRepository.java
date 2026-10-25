package com.student.IdentityService.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.student.IdentityService.model.Enum.MembershipStatus;
import com.student.IdentityService.model.OrganizationMembership;

public interface OrganizationMembershipRepository extends JpaRepository<OrganizationMembership, Long> {

    List<OrganizationMembership> findAllByUser_IdAndStatus(Long userId, MembershipStatus status);

    Optional<OrganizationMembership> findByIdAndOrganization_Id(
            Long membershipId,
            Long organizationId);

    Optional<OrganizationMembership> findByIdAndOrganization_IdAndUser_Id(
            Long membershipId,
            Long organizationId,
            Long userId);

    boolean existsByUser_IdAndOrganization_IdAndStatusAndMembershipRoles_Role_Name(
            Long userId,
            Long organizationId,
            MembershipStatus status,
            String roleName);
}
