package com.student.IdentityService.repository;

import com.student.IdentityService.model.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PrivilegeRepo extends JpaRepository<Privilege, Long> {

    boolean existsByName(String name);

    Optional<Privilege> findByName(String name);

}
