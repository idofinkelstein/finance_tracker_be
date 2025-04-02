package com.ido.financetracker.finance.service;

import com.ido.financetracker.auth.entity.User;
import com.ido.financetracker.auth.security.SecurityUtils;
import com.ido.financetracker.category.dto.CategoryResponse;
import com.ido.financetracker.finance.dto.ExpensesByCategoryResponse;
import com.ido.financetracker.transaction.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class FinancialSummaryService {
    private final TransactionRepository transactionRepository;
    private final SecurityUtils securityUtils;

    public FinancialSummaryService(TransactionRepository transactionRepository, SecurityUtils securityUtils) {
        this.transactionRepository = transactionRepository;
        this.securityUtils = securityUtils;
    }

    public List<ExpensesByCategoryResponse> getExpensesByCategory(LocalDate startDate, LocalDate endDate) {
        User user = securityUtils.getUserFromAuthentication();
        if (startDate == null) {
            startDate = LocalDate.EPOCH;
        }
        if (endDate == null) {
            endDate = LocalDate.now();
        }
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }
        List<Object[]> result = transactionRepository.sumAmountByCategoryBetweenDatesForUser(user.getId(), startDate, endDate);

        return result.stream().map(record -> {
            if (record.length != 3) {
                throw new IllegalArgumentException("Invalid data in the result set");
            }
            Long categoryId = (Long) record[2];
            String categoryName = (String) record[1];
            BigDecimal totalAmount = (BigDecimal) record[0];
            CategoryResponse categoryResponse = new CategoryResponse(categoryId, categoryName);

            return ExpensesByCategoryResponse.builder().category(categoryResponse).totalAmount(totalAmount).build();
        }).toList();
    }
}
