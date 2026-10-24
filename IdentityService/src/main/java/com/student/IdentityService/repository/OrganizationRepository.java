package com.student.IdentityService.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.student.IdentityService.model.Organization;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {
    Optional<Organization> findByName(String name);

    boolean existsByName(String trim);

}
