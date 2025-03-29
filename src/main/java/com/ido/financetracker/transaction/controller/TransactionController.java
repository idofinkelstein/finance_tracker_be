package com.ido.financetracker.transaction.controller;

import com.ido.financetracker.auth.entity.User;
import com.ido.financetracker.auth.repository.UserRepository;
import com.ido.financetracker.category.service.CategoryService;
import com.ido.financetracker.transaction.dto.TransactionRequest;
import com.ido.financetracker.transaction.dto.TransactionResponse;
import com.ido.financetracker.transaction.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<TransactionResponse> postTransaction(@RequestBody TransactionRequest transactionRequest) {
        // Implement your transaction logic here


        TransactionResponse transactionResponse = transactionService.postTransaction(transactionRequest);

        return new ResponseEntity<>(transactionResponse, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Iterable<TransactionResponse>> getAllTransactions() {
        // Implement your transaction retrieval logic here
        // need to extract user information from the request and return all transactions related to the user
        List<TransactionResponse> transactions = transactionService.getAll();

        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponse> getTransaction(@PathVariable String id) {
        // Implement your transaction retrieval logic here
        // need to extract user information from the request and return the transaction with the given ID
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable String id) {
        // Implement your transaction deletion logic here
        // need to extract user information from the request and delete the transaction with the given ID
        return ResponseEntity.noContent().build();
    }
}
