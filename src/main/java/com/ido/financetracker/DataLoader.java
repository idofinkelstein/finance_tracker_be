package com.ido.financetracker;

import com.ido.financetracker.auth.repository.UserRepository;
import com.ido.financetracker.auth.security.JwtService;
import com.ido.financetracker.category.entity.Category;
import com.ido.financetracker.category.repository.CategoryRepository;
import com.ido.financetracker.transaction.dto.TransactionType;
import com.ido.financetracker.transaction.entity.Transaction;
import com.ido.financetracker.transaction.repository.TransactionRepository;
import com.ido.financetracker.auth.entity.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final TransactionRepository transactionRepository;

    private final JwtService jwtService;

    public DataLoader(UserRepository userRepository, CategoryRepository categoryRepository, TransactionRepository transactionRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.transactionRepository = transactionRepository;
        this.jwtService = jwtService;
    }

    @Override
    public void run(String... args) throws Exception {
        // Load initial data into the database
        User user = new User(
                "user1",
                jwtService.passwordEncoder().encode("password"),
                "email@mail.com",
                LocalDate.now());
        userRepository.save(user);

        Category category = categoryRepository.save(Category.builder().user(user).name("food").build());

        Transaction transaction = Transaction.builder().user(user).
                amount(BigDecimal.valueOf(100)).
                date(LocalDate.now()).
                transactionType(TransactionType.EXPENSE).
                category(category).
                description("Test transaction").
                build();
        transactionRepository.save(transaction);
    }
}

