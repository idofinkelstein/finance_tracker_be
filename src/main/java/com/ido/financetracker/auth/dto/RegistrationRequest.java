package com.ido.financetracker.auth.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RegistrationRequest(
        @NotBlank
        String username,
        @NotNull @Size(min = 6, max = 24)
        String password,
        @Email(message = "You must enter a valid email address.")
        String email
) {
}