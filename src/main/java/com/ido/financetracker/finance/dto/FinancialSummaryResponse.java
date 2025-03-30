package com.ido.financetracker.finance.dto;

import java.math.BigDecimal;

public record FinancialSummaryResponse(
        BigDecimal totalIncome,
        BigDecimal totalExpenses,
        BigDecimal netBalance
) {
}
