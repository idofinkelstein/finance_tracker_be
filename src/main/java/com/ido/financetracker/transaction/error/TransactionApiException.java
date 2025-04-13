package com.ido.financetracker.transaction.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class TransactionApiException extends ResponseStatusException {


    public TransactionApiException(HttpStatus status, String reason) {
        super(status, reason);
    }
}
