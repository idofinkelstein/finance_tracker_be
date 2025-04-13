package com.ido.financetracker.category.error;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class CategoryExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<Object> handleCategoryException(CategoryApiException categoryApiException, HttpServletRequest request) {

        final HttpStatusCode statusCode = categoryApiException.getStatusCode();
        final Map<String, Object> errors = new HashMap<>();

        errors.put("error", "unauthorized");
        errors.put("message", categoryApiException.getReason());
        errors.put("status", statusCode);
        errors.put("path", request.getServletPath());
        errors.put("time", LocalDateTime.now());

        return new ResponseEntity<>(errors, statusCode);
    }
}
