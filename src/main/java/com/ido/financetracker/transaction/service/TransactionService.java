package com.ido.financetracker.transaction.service;

import com.ido.financetracker.auth.entity.User;
import com.ido.financetracker.auth.security.SecurityUtils;
import com.ido.financetracker.category.dto.CategoryResponse;
import com.ido.financetracker.category.entity.Category;
import com.ido.financetracker.category.repository.CategoryRepository;
import com.ido.financetracker.transaction.dto.TransactionRequest;
import com.ido.financetracker.transaction.dto.TransactionResponse;
import com.ido.financetracker.transaction.entity.Transaction;
import com.ido.financetracker.transaction.error.TransactionApiException;
import com.ido.financetracker.transaction.repository.TransactionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final CategoryRepository categoryRepository;
    private final SecurityUtils securityUtils;

    public TransactionService(TransactionRepository transactionRepository, CategoryRepository categoryRepository, SecurityUtils securityUtils) {
        this.transactionRepository = transactionRepository;
        this.categoryRepository = categoryRepository;
        this.securityUtils = securityUtils;
    }

    public TransactionResponse postTransaction(TransactionRequest transactionRequest) {

        User user = securityUtils.getUserFromAuthentication();

        Category category = categoryRepository.findById(transactionRequest.categoryId()).orElseThrow();

        Transaction createdTransaction = Transaction.builder()
                .user(user)
                .date(transactionRequest.date())
                .amount(transactionRequest.amount())
                .description(transactionRequest.description())
                .category(category)
                .transactionType(transactionRequest.transactionType())
                .build();

        createdTransaction = transactionRepository.save(createdTransaction);

        CategoryResponse categoryResponse = new CategoryResponse(category.getId(), category.getName());

        return new TransactionResponse(
                createdTransaction.getId(),
                createdTransaction.getDate(),
                createdTransaction.getAmount(),
                createdTransaction.getDescription(),
                categoryResponse,
                transactionRequest.transactionType()
        );
    }

    public List<TransactionResponse> getAll() {

        User user = securityUtils.getUserFromAuthentication();
        ArrayList<Transaction> transactions = transactionRepository.findAllByUserId(user.getId());

        return transactions.stream().map(transaction -> {
            CategoryResponse categoryResponse = new CategoryResponse(transaction.getCategory().getId()
                    , transaction.getCategory().getName());
            return new TransactionResponse(
                    transaction.getId(),
                    transaction.getDate(),
                    transaction.getAmount(),
                    transaction.getDescription(),
                    categoryResponse,
                    transaction.getTransactionType()
            );
        }).toList();
    }

    public TransactionResponse getTransaction(Long id) {
        User user = securityUtils.getUserFromAuthentication();
        Transaction transaction = transactionRepository
                .findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new TransactionApiException(HttpStatus.FORBIDDEN,"You are not allowed to access this resource"));

        CategoryResponse categoryResponse = new CategoryResponse(
                transaction.getCategory().getId(),
                transaction.getCategory().getName());

        return new TransactionResponse(
                transaction.getId(),
                transaction.getDate(),
                transaction.getAmount(),
                transaction.getDescription(),
                categoryResponse,
                transaction.getTransactionType()
        );
    }

    public ResponseEntity<String> deleteTransaction(Long id) {
        User user = securityUtils.getUserFromAuthentication();

        int rowsAffected = transactionRepository.deleteByIdAndUserId(id, user.getId());

        if (rowsAffected == 0) {
            throw new TransactionApiException(HttpStatus.FORBIDDEN, "You are not authorized to delete this transaction or transaction not found");
        }

        return ResponseEntity.ok("Transaction deleted successfully");
    }
}
