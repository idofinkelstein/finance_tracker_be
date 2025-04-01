package com.ido.financetracker.transaction.repository;

import com.ido.financetracker.transaction.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    ArrayList<Transaction> findAllByUserId(Long userId);

}
