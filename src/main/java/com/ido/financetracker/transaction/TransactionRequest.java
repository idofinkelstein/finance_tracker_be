package com.ido.financetracker.transaction;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TransactionRequest(
        LocalDate date,
        BigDecimal amount,
        String description,
        Long categoryId,
        TransactionType transactionType // "INCOME" or "EXPENSE"
) {
}
