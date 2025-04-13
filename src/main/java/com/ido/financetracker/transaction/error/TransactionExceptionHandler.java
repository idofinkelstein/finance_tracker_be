package com.ido.financetracker.transaction.error;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class TransactionExceptionHandler {

    @ExceptionHandler(value = TransactionApiException.class)
    ResponseEntity<Object> handleTransactionException(TransactionApiException transactionApiException, HttpServletRequest request) {

        HttpStatusCode statusCode = transactionApiException.getStatusCode();
        final Map<String, Object> errors = new HashMap<>();

        errors.put("error", "unauthorized");
        errors.put("message", transactionApiException.getReason());
        errors.put("status", statusCode);
        errors.put("path", request.getServletPath());
        errors.put("time", LocalDateTime.now());

        return new ResponseEntity<>(errors, statusCode);
    }
}
