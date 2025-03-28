package com.ido.financetracker.transaction.service;

import com.ido.financetracker.auth.entity.User;
import com.ido.financetracker.auth.repository.UserRepository;
import com.ido.financetracker.category.entity.Category;
import com.ido.financetracker.category.dto.CategoryResponse;
import com.ido.financetracker.category.repository.CategoryRepository;
import com.ido.financetracker.transaction.dto.TransactionRequest;
import com.ido.financetracker.transaction.dto.TransactionResponse;
import com.ido.financetracker.transaction.entity.Transaction;
import com.ido.financetracker.transaction.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TransactionService {

    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    private final CategoryRepository categoryRepository;


    public TransactionService(UserRepository userRepository, TransactionRepository transactionRepository, CategoryRepository categoryRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }


    public TransactionResponse postTransaction(TransactionRequest transactionRequest, String username) {

        // Validate and sanitize the transaction data

        // Find the user by their username
        User user = userRepository.findByUsernameOrEmail(username, username);

        // If the user does not exist, throw an exception
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        Optional<Category> optionalCategory = categoryRepository.findById(transactionRequest.categoryId());

        if(optionalCategory.isEmpty()) {
            throw new IllegalArgumentException("Category not found");
        }
        Category category = optionalCategory.get();
        // Create a new Transaction entity from the request data
        Transaction createdTransaction = Transaction.builder()
               .user(user)
               .date(transactionRequest.date())
               .amount(transactionRequest.amount())
               .description(transactionRequest.description())
               .category(category)
               .transactionType(transactionRequest.transactionType())
               .build();

        // Save the transaction to the database
        transactionRepository.save(createdTransaction);

        CategoryResponse categoryResponse = new CategoryResponse(category.id(), category.name());
        // Create a new TransactionResponse object from the saved transaction data

        return new TransactionResponse(
                createdTransaction.getId(),
                createdTransaction.getDate(),
                createdTransaction.getAmount() ,
                createdTransaction.getDescription(),
                categoryResponse,
                transactionRequest.transactionType()
        );

    }
}
