package com.student.IdentityService.serviceImpl;

import com.student.IdentityService.jwt.JwtService;
import com.student.IdentityService.model.Role;
import com.student.IdentityService.model.Users;
import com.student.IdentityService.repository.RoleRepo;
import com.student.IdentityService.repository.UsersRepo;
import com.student.IdentityService.request.UserCreationRequest;
import com.student.IdentityService.response.LoginResponse;
import com.student.IdentityService.response.UserResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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


    @Transactional
    public UserResponse createUser(UserCreationRequest request) {
        String email = request.email().trim().toLowerCase();
        String mobile = request.mobileNumber().trim();

        if (userRepository.existsByEmail(email)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email is already registered");
        }
        if (userRepository.existsByMobile(mobile)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Mobile number is already registered");
        }

        Role defaultRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "Default user role is not initialized"));

        Users user = new Users();
        String firstName = request.firstName().trim();
        String lastName = request.lastName() == null ? "" : request.lastName().trim();
        user.setUsername((firstName + " " + lastName).trim());
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setMobile(mobile);
        user.getRoles().add(defaultRole);

        Users savedUser = userRepository.save(user);
        return new UserResponse(
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getEmail(),
                savedUser.getMobile(),
                savedUser.getRoles().stream().map(Role::getName).toList());
    }


    public LoginResponse LoginUser(String identifier, String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(identifier, password));
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String jwt = jwtService.generateToken(userDetails);
            return new LoginResponse(jwt, "Bearer", jwtService.getExpirationTime() / 1000);
        } catch (AuthenticationException exception) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }
    }

}
