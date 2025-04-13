package com.ido.financetracker.transaction.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TransactionRequest(
//        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate date,
        BigDecimal amount,
        String description,
        Long categoryId,
        TransactionType transactionType // "INCOME" or "EXPENSE"
) {
}
