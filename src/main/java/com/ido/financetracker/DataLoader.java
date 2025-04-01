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
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Component
@Profile("dev")
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final TransactionRepository transactionRepository;
    private final JwtService jwtService;
    private final Random random = new Random();

    public DataLoader(UserRepository userRepository, CategoryRepository categoryRepository, TransactionRepository transactionRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.transactionRepository = transactionRepository;
        this.jwtService = jwtService;
    }

    @Override
    public void run(String... args) throws Exception {
        // Create 5 users
        List<User> users = Arrays.asList(
            createUser("john_doe", "password123", "john@example.com"),
            createUser("jane_smith", "password123", "jane@example.com"),
            createUser("bob_wilson", "password123", "bob@example.com"),
            createUser("alice_brown", "password123", "alice@example.com"),
            createUser("charlie_davis", "password123", "charlie@example.com")
        );

        // Save all users
        users.forEach(userRepository::save);

        // Create categories and transactions for each user
        users.forEach(this::createUserData);
    }

    private User createUser(String username, String password, String email) {
        return new User(
            username,
            jwtService.passwordEncoder().encode(password),
            email,
            LocalDate.now().minusDays(random.nextInt(365))
        );
    }

    private void createUserData(User user) {
        // Define possible categories for each user
        List<String> expenseCategories = Arrays.asList(
            "Food & Dining",
            "Transportation",
            "Housing",
            "Utilities",
            "Entertainment",
            "Healthcare",
            "Shopping",
            "Education"
        );

        List<String> incomeCategories = Arrays.asList(
            "Salary",
            "Freelance",
            "Investments",
            "Gifts",
            "Rental Income"
        );

        // Create 2-4 expense categories for the user
        int numExpenseCategories = random.nextInt(3) + 2; // 2-4 categories
        for (int i = 0; i < numExpenseCategories; i++) {
            String categoryName = expenseCategories.get(random.nextInt(expenseCategories.size()));
            Category category = Category.builder()
                .user(user)
                .name(categoryName)
                .build();
            categoryRepository.save(category);

            // Create 3-5 transactions for this category
            createTransactionsForCategory(user, category, TransactionType.EXPENSE);
        }

        // Create 1-2 income categories for the user
        int numIncomeCategories = random.nextInt(2) + 1; // 1-2 categories
        for (int i = 0; i < numIncomeCategories; i++) {
            String categoryName = incomeCategories.get(random.nextInt(incomeCategories.size()));
            Category category = Category.builder()
                .user(user)
                .name(categoryName)
                .build();
            categoryRepository.save(category);

            // Create 2-3 transactions for this category
            createTransactionsForCategory(user, category, TransactionType.INCOME);
        }
    }

    private void createTransactionsForCategory(User user, Category category, TransactionType type) {
        int numTransactions = type == TransactionType.EXPENSE ? 
            random.nextInt(3) + 3 : // 3-5 expense transactions
            random.nextInt(2) + 2;  // 2-3 income transactions

        for (int i = 0; i < numTransactions; i++) {
            // Generate random date within the last 30 days
            LocalDate date = LocalDate.now().minusDays(random.nextInt(30));
            
            // Generate random amount based on transaction type
            BigDecimal amount;
            if (type == TransactionType.EXPENSE) {
                amount = BigDecimal.valueOf(random.nextInt(1000) + 10); // 10-1010
            } else {
                amount = BigDecimal.valueOf(random.nextInt(5000) + 1000); // 1000-6000
            }

            Transaction transaction = Transaction.builder()
                .user(user)
                .date(date)
                .amount(amount)
                .category(category)
                .transactionType(type)
                .description(generateDescription(type, category.getName(), amount))
                .build();

            transactionRepository.save(transaction);
        }
    }

    private String generateDescription(TransactionType type, String category, BigDecimal amount) {
        String[] templates = {
            "%s transaction for %s",
            "Monthly %s in %s category",
            "Regular %s for %s",
            "%s payment - %s"
        };
        String template = templates[random.nextInt(templates.length)];
        return String.format(template, 
            type == TransactionType.EXPENSE ? "Expense" : "Income",
            category);
    }
}

