package com.ido.financetracker.finance.dto;

import com.ido.financetracker.category.dto.CategoryResponse;

import java.math.BigDecimal;

public record ExpensesByCategoryResponse(
        CategoryResponse category,
        BigDecimal totalAmount
) {
}
