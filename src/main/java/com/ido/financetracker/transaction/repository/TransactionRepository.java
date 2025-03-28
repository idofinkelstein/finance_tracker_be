package com.ido.financetracker.transaction.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository<Transaction, Long> extends JpaRepository<Transaction, Long> {
}
