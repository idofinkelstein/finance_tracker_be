package com.ido.financetracker.category.service;

import com.ido.financetracker.auth.entity.User;
import com.ido.financetracker.auth.repository.UserRepository;
import com.ido.financetracker.auth.security.SecurityUtils;
import com.ido.financetracker.category.dto.CategoryRequest;
import com.ido.financetracker.category.dto.CategoryResponse;
import com.ido.financetracker.category.entity.Category;
import com.ido.financetracker.category.repository.CategoryRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    private final SecurityUtils securityUtils;

    public CategoryService(CategoryRepository categoryRepository, SecurityUtils securityUtils) {
        this.categoryRepository = categoryRepository;
        this.securityUtils = securityUtils;
    }

    public CategoryResponse postCategory(CategoryRequest categoryRequest) {

        User user = securityUtils.getUserFromAuthentication();

        Category category = new Category(
                user,
                categoryRequest.name());
        category = categoryRepository.save(category);

        return new CategoryResponse(category.getId(), category.getName());
    }

    public CategoryResponse getCategory(String categoryName) {

        User user = securityUtils.getUserFromAuthentication();

        Category category = categoryRepository.findByNameAndUserId(categoryName, user.getId()).orElseThrow();

        return new CategoryResponse(category.getId(), category.getName());
    }
}
