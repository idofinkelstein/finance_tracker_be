package com.ido.financetracker.category;

import com.ido.financetracker.transaction.Transaction;
import com.ido.financetracker.auth.entity.User;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "categories")
public record Category(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        Long id,
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id", nullable = false)
        User user,
        @Column(nullable = false)
        String name,
        @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
        Set<Transaction> transactions
) {
    public Category() {
        this(null, null, null, null);
    }

    public Category(User user, String name) {
        this(null, user, name, null);
    }
}