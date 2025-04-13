package com.ido.financetracker.category.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CategoryApiException extends ResponseStatusException {
    public CategoryApiException(HttpStatus status, String reason) {
        super(status, reason);
    }
}
