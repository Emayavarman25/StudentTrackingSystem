package com.student.IdentityService.jwt;

import com.student.IdentityService.model.Users;
import com.student.IdentityService.repository.UsersRepo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class ApplicationConfiguration {
    private final UsersRepo userRepository;

    public ApplicationConfiguration(UsersRepo userRepository) {
        this.userRepository = userRepository;
    }
@Bean
public UserDetailsService userDetailsService() {
    return username -> {
    Users user = userRepository.findByIdentifierWithRolesAndPrivileges(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found"));

        return new CustomUserDetails(user);
    };
}

@Bean
public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}

@Bean
public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider provider =
            new DaoAuthenticationProvider(userDetailsService());

    provider.setPasswordEncoder(passwordEncoder());
    return provider;
}

@Bean
public AuthenticationManager authenticationManager(
        AuthenticationConfiguration configuration) throws Exception {
    return configuration.getAuthenticationManager();
}
}