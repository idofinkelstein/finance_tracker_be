package com.ido.financetracker.auth.dto;


public record LoginRequest(
        String usernameOrEmail,
        String password
) {
}