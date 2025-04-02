package com.ido.financetracker.transaction.repository;

import com.ido.financetracker.transaction.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    ArrayList<Transaction> findAllByUserId(Long userId);

    @Query("""
            SELECT SUM(t.amount), c.name, c.id
            FROM Transaction t
            JOIN t.category c
            WHERE t.user.id = :userId AND date BETWEEN :startDate AND :endDate
            GROUP BY c.name""")
    List<Object[]> sumAmountByCategoryBetweenDatesForUser(
            @Param("userId") Long userId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );
}
