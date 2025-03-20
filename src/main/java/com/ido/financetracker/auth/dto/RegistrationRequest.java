package com.ido.financetracker.auth.dto;

public record RegistrationRequest(
        String username,
        String password,
        String email
) {
}