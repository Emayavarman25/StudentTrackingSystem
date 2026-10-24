package com.student.IdentityService.serviceImpl;

import com.student.IdentityService.model.Users;
import com.student.IdentityService.repository.UsersRepo;
import com.student.IdentityService.service.UsersCreations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UsersCreationImpl implements UsersCreations {
    public UsersCreationImpl(UsersRepo userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    private final UsersRepo userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;


    @Override
    public Users signup(Users input) {
        return userRepository.save(input);
    }

    @Override
    public Users signIn(Users input) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(input.getEmail(), input.getPassword()));

        return userRepository.findByEmail(input.getEmail())
                .orElseThrow();
    }
}

