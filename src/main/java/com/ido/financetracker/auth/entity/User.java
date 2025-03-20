package com.ido.financetracker.auth.entity;


import jakarta.persistence.*;
import jdk.jfr.Name;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "users")
public record User(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        Long id,
        @Column(nullable = false, unique = true)
        String username,
        @Column(nullable = false)
        String password,
        @Column(nullable = false, unique = true)
        String email,
        @Column(nullable = false)
        @Name("registration_date")
        LocalDate registrationDate
) {
    // JPA requires a no-args constructor.
    // This compact constructor achieves that.
    public User() {
        this(null, null, null, null, null);
    }

    public User(String username, String password, String email) {
        this(null, username, password, email, LocalDate.now());
    }
}