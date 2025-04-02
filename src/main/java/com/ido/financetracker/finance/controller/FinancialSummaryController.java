package com.ido.financetracker.finance.controller;

import com.ido.financetracker.finance.dto.ExpensesByCategoryResponse;
import com.ido.financetracker.finance.service.FinancialSummaryService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/summaries")
public class FinancialSummaryController {

    private final FinancialSummaryService financialSummaryService;

    public FinancialSummaryController(FinancialSummaryService financialSummaryService) {
        this.financialSummaryService = financialSummaryService;
    }

    @GetMapping("/expenses-by-category")
    public ResponseEntity<List<ExpensesByCategoryResponse>>
    getExpensesByCategory(
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate endDate) {
        List<ExpensesByCategoryResponse> result = financialSummaryService.getExpensesByCategory(startDate, endDate);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
