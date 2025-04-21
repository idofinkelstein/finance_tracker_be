package com.ido.financetracker.category.dto;

import jakarta.validation.constraints.Size;

public record CategoryRequest(
        @Size(max = 32, message = "Category name must be max 32 character long!")
        String name
) {
}