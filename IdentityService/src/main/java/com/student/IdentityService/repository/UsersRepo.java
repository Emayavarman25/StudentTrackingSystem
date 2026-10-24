package com.student.IdentityService.repository;

import com.student.IdentityService.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UsersRepo extends JpaRepository<Users, Long> {
    Optional<Users> findByUsername(String username);
    Optional<Users> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByMobile(String mobile);

    @Query("""
    SELECT DISTINCT u
    FROM Users u
    LEFT JOIN FETCH u.roles r
    LEFT JOIN FETCH r.privileges
    WHERE u.email = :email
    """)
Optional<Users> findByEmailWithRolesAndPrivileges( @Param("email") String email);



}
