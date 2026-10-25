package com.student.IdentityService.initialisation;


import com.student.IdentityService.model.Role;
import com.student.IdentityService.model.Privilege;
import com.student.IdentityService.repository.RoleRepo;
import com.student.IdentityService.repository.PrivilegeRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Set;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(
            RoleRepo roleRepo,
            PrivilegeRepo privilegeRepo
    ) {
        return args -> {
            Privilege readPrivilege = privilegeRepo.findByName("READ_PRIVILEGE")
                    .orElseGet(() -> privilegeRepo.save(new Privilege("READ_PRIVILEGE")));
            Privilege writePrivilege = privilegeRepo.findByName("WRITE_PRIVILEGE")
                    .orElseGet(() -> privilegeRepo.save(new Privilege("WRITE_PRIVILEGE")));

            roleRepo.findByName("ROLE_ADMIN")
                    .orElseGet(() -> roleRepo.save(
                            new Role("ROLE_ADMIN", Set.of(readPrivilege, writePrivilege))));
            roleRepo.findByName("ROLE_USER")
                    .orElseGet(() -> roleRepo.save(new Role("ROLE_USER", Set.of(readPrivilege))));

            roleRepo.findByName("ORG_ADMIN")
                    .orElseGet(() -> roleRepo.save(
                            new Role("ORG_ADMIN", Set.of(readPrivilege, writePrivilege))));
            roleRepo.findByName("ORG_USER")
                    .orElseGet(() -> roleRepo.save(new Role("ORG_USER", Set.of(readPrivilege))));
        };
    }
}