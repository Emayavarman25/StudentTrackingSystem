package com.student.IdentityService.repository;

import com.student.IdentityService.model.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrivilegeRepo extends JpaRepository<Privilege, Long> {

    boolean existsByName(String name);

}
