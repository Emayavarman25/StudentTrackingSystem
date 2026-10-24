package com.student.IdentityService.serviceImpl;

import com.student.IdentityService.jwt.JwtService;
import com.student.IdentityService.model.Role;
import com.student.IdentityService.model.Users;
import com.student.IdentityService.repository.RoleRepo;
import com.student.IdentityService.repository.UsersRepo;
import com.student.IdentityService.request.UserCreationRequest;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsersCreationImpl {
    
    public UsersCreationImpl(UsersRepo userRepository, PasswordEncoder passwordEncoder, RoleRepo roleRepository, JwtService jwtService,AuthenticationManager authenticationManager ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }
    private final AuthenticationManager authenticationManager;

    private final UsersRepo userRepository;
    private final RoleRepo roleRepository;

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;


    public String createUser(UserCreationRequest userCreaionRequest) {

        try {
            if (userRepository.existsByEmail(userCreaionRequest.email())
                    || userRepository.existsByMobile(userCreaionRequest.mobileNumber())) {
                throw new RuntimeException("User with email " + userCreaionRequest.email() + " or mobile number "
                        + userCreaionRequest.mobileNumber() + " already exists.");
            }

            Users user = new Users();
            user.setUsername(userCreaionRequest.firstName());
            user.setEmail(userCreaionRequest.email());
            user.setPassword(passwordEncoder.encode(userCreaionRequest.password()));
            user.setMobile(userCreaionRequest.mobileNumber());
            Role role = roleRepository.findByName("PLATFORM_ADMIN")
                    .orElseThrow(() -> new IllegalArgumentException("Role not found"));

            Set<Role> roles = new HashSet<>();
            roles.add(role);
            user.setRoles(roles);
            userRepository.save(user);
            return "User created successfully";

        } catch (Exception e) {
         return    "Error creating user: " + e.getMessage();
        }

    }


    public String LoginUser(String email, String password) {
        try {
            System.out.println(
    "AuthenticationManager: " + authenticationManager
);
                Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password));
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                String jwt = jwtService.generateToken(userDetails);

            return  jwt;
        } catch (Exception e) {
            return "Error logging in user: " + e.getMessage();
        }
    }

}
