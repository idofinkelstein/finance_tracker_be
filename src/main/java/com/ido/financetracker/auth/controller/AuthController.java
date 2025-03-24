package com.ido.financetracker.auth.controller;

import com.ido.financetracker.auth.dto.AuthenticationResponse;
import com.ido.financetracker.auth.dto.LoginRequest;
import com.ido.financetracker.auth.dto.RegistrationRequest;
import com.ido.financetracker.auth.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    // Implement endpoints for authentication and authorization
    private final AuthService authService;


    public AuthController(AuthService service) {
        this.authService = service;
    }

    @GetMapping("/hello")
    public ResponseEntity<String> helloWorld() {
        return ResponseEntity.ok("hello, world!");
    }

    @GetMapping("/welcome")
    public ResponseEntity<String> welcome() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok("Welcome, " + auth.getName() + " Validated User to Finance Tracker API!");
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegistrationRequest registrationRequest) {
        // Implement registration logic here and return appropriate response with status code 201 Created if successful, or 400 Bad Request if validation fails.

        AuthenticationResponse response = authService.register(registrationRequest);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequest loginRequest) {
        // Implement login logic here and return appropriate response with status code 200 OK if successful, or 400 Bad Request if validation fails.

        AuthenticationResponse response = authService.login(loginRequest);
        return new ResponseEntity<>(authService.login(loginRequest), HttpStatus.OK);
    }
}
