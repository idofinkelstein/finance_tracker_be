package com.ido.financetracker.transaction.repository;

import com.ido.financetracker.transaction.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    Optional<Transaction> findByIdAndUserId(Long id, Long userId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Transaction t WHERE t.id = :id AND t.user.id = :userId")
    int deleteByIdAndUserId(Long id, Long userId);
}
