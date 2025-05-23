package com.ido.financetracker.auth.service;

import com.ido.financetracker.auth.dto.AuthenticationResponse;
import com.ido.financetracker.auth.dto.LoginRequest;
import com.ido.financetracker.auth.dto.RegistrationRequest;
import com.ido.financetracker.auth.entity.User;
import com.ido.financetracker.auth.repository.UserRepository;
import com.ido.financetracker.auth.security.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository, JwtService jwtService, UserDetailsService userDetailsService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
    }


    public AuthenticationResponse register(RegistrationRequest registrationRequest) {

        if (userRepository.existsByUsername(registrationRequest.username())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists");
        }
        if (userRepository.existsByEmail(registrationRequest.email())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists");
        }

        User user = new User(registrationRequest.username(), jwtService.passwordEncoder().encode(registrationRequest.password()), registrationRequest.email(), LocalDate.now());
        userRepository.save(user);
        String authenticationString = jwtService.generateToken(userDetailsService.loadUserByUsername(registrationRequest.username()));
        return new AuthenticationResponse(authenticationString);
    }

    public AuthenticationResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.usernameOrEmail(),
                        loginRequest.password()));

        if (authentication.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails userDetails = (UserDetails)(authentication.getPrincipal());
            String jwtToken = jwtService.generateToken(userDetails);
            return new AuthenticationResponse(jwtToken);
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
    }
}
