package com.ido.financetracker.transaction;

import com.ido.financetracker.category.Category;
import com.ido.financetracker.auth.entity.User;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "transactions")
public record Transaction(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        Long id,
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id", nullable = false)
        User user,
        @Column(nullable = false)
        LocalDate date,
        @Column(nullable = false, precision = 10, scale = 2)
        BigDecimal amount,
        String description,
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "category_id", nullable = false)
        Category category,
        @Column(nullable = false)
        @Enumerated(EnumType.STRING)
        TransactionType transactionType
) {
    public Transaction() {
        this(null, null, null, null, null, null, null);
    }

    public Transaction(User user, LocalDate date, BigDecimal amount, String description, Category category, TransactionType transactionType) {
        this(null, user, date, amount, description, category, transactionType);
    }
}