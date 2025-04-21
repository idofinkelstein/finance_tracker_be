package com.ido.financetracker.transaction.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TransactionRequest(
        LocalDate date,
        @DecimalMin(value = "0.1")
        BigDecimal amount,
        @Size(max = 256, message = "Description must not exceed 256 characters")
        String description,
        Long categoryId,
        TransactionType transactionType // "INCOME" or "EXPENSE"
) {
}
