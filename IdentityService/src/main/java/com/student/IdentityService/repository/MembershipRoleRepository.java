package com.student.IdentityService.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.student.IdentityService.model.MembershipRole;

public interface MembershipRoleRepository extends JpaRepository<MembershipRole, Long> {

    boolean existsByMembership_IdAndRole_Id(Long membershipId, Long roleId);

    List<MembershipRole> findAllByMembership_Id(Long membershipId);
}
