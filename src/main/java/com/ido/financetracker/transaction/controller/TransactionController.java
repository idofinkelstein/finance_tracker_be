package com.ido.financetracker.transaction.controller;

import com.ido.financetracker.transaction.dto.TransactionRequest;
import com.ido.financetracker.transaction.dto.TransactionResponse;
import com.ido.financetracker.transaction.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        List<TransactionResponse> transactions = transactionService.getAll();

        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponse> getTransaction(@PathVariable Long id) {

        TransactionResponse transactionResponse = transactionService.getTransactionById(id);

        return new ResponseEntity<>(transactionResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {

        transactionService.deleteTransaction(id);

        return ResponseEntity.noContent().build();
    }
}
