package com.ido.financetracker.auth.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class JwtExceptionHandler {

    @ExceptionHandler(value = {ExpiredJwtException.class, BadCredentialsException.class, SignatureException.class})
    public ResponseEntity<Object> handleJwtException(Exception jwtException, HttpServletRequest request) {
        final HttpStatus unauthorized = HttpStatus.UNAUTHORIZED;
        final Map<String, Object> errors = new HashMap<>();

        errors.put("error", "unauthorized");
        errors.put("message", jwtException.getMessage());
        errors.put("status", unauthorized);
        errors.put("path", request.getServletPath());
        errors.put("time", LocalDateTime.now());

        return new ResponseEntity<>(errors, unauthorized);
    }
}
