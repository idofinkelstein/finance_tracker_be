package com.ido.financetracker.transaction.entity;
import com.ido.financetracker.category.entity.Category;
import com.ido.financetracker.auth.entity.User;
import com.ido.financetracker.transaction.dto.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "transactions")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    public Transaction(User user, LocalDate date, BigDecimal amount, String description, Category category, TransactionType transactionType) {
        this.user = user;
        this.date = date;
        this.amount = amount;
        this.description = description;
        this.category = category;
        this.transactionType = transactionType;
    }
}