package com.student.IdentityService.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.student.IdentityService.model.Enum.MembershipStatus;
import com.student.IdentityService.model.OrganizationMembership;

public interface OrganizationMembershipRepository extends JpaRepository<OrganizationMembership, Long> {

    List<OrganizationMembership> findAllByUser_IdAndStatus(Long userId, MembershipStatus status);
}
