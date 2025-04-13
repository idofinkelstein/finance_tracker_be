package com.ido.financetracker.finance.dto;

import com.ido.financetracker.category.dto.CategoryResponse;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ExpensesByCategoryResponse(
        CategoryResponse category,
        BigDecimal totalAmount
) {
}
