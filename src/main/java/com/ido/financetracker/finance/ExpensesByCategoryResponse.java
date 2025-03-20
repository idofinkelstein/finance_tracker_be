package com.ido.financetracker.finance;

import com.ido.financetracker.category.CategoryResponse;

import java.math.BigDecimal;

public record ExpensesByCategoryResponse(
        CategoryResponse category,
        BigDecimal totalAmount
) {
}
