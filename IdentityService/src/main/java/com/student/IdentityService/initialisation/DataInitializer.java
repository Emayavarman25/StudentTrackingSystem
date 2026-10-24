package com.student.IdentityService.initialisation;


import com.student.IdentityService.model.Users;
import com.student.IdentityService.model.Role;
import com.student.IdentityService.model.Privilege;
import com.student.IdentityService.repository.UsersRepo;
import com.student.IdentityService.repository.RoleRepo;
import com.student.IdentityService.repository.PrivilegeRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(
            UsersRepo usersRepo,
            RoleRepo roleRepo,
            PrivilegeRepo privilegeRepo,
            PasswordEncoder passwordEncoder
    ) {
        return args -> {
            if(usersRepo.count() > 0) return;
            // Create privileges
            Privilege readPrivilege = privilegeRepo.save(new Privilege("READ_PRIVILEGE"));
            Privilege writePrivilege = privilegeRepo.save(new Privilege("WRITE_PRIVILEGE"));

            // Create roles
            Role adminRole = new Role("ROLE_ADMIN", Set.of(readPrivilege, writePrivilege));
            Role userRole = new Role("ROLE_USER", Set.of(readPrivilege));
            roleRepo.save(adminRole);
            roleRepo.save(userRole);

            // Create users
            Users admin = new Users();
            admin.setEmail("emaya@gmail.com");
            admin.setPassword(passwordEncoder.encode("emaya@123"));
            admin.setRoles(Set.of(adminRole));
            usersRepo.save(admin);

            Users user = new Users();
            user.setEmail("mani@gmail.com");
            user.setPassword(passwordEncoder.encode("mani@123"));
            user.setRoles(Set.of(userRole));
            usersRepo.save(user);
        };
    }
}