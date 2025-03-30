package com.ido.financetracker.transaction.dto;


import com.ido.financetracker.category.dto.CategoryResponse;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TransactionResponse(
        Long id,
        LocalDate date,
        BigDecimal amount,
        String description,
        CategoryResponse category, // Nested Category information
        TransactionType transactionType
) {
}