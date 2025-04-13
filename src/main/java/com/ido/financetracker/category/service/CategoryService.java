package com.ido.financetracker.category.service;

import com.ido.financetracker.auth.entity.User;
import com.ido.financetracker.auth.security.SecurityUtils;
import com.ido.financetracker.category.dto.CategoryRequest;
import com.ido.financetracker.category.dto.CategoryResponse;
import com.ido.financetracker.category.entity.Category;
import com.ido.financetracker.category.error.CategoryApiException;
import com.ido.financetracker.category.repository.CategoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public CategoryResponse getCategory(Long categoryId) {

        User user = securityUtils.getUserFromAuthentication();

        Category category = categoryRepository.findByIdAndUserId(categoryId, user.getId()).
                orElseThrow(() -> new CategoryApiException(HttpStatus.FORBIDDEN, "Category not found or you don't have access to the resource"));

        return  new CategoryResponse(category.getId(), category.getName());
    }

    public List<CategoryResponse> getAll() {
        User user = securityUtils.getUserFromAuthentication();

        List<Category> categories = categoryRepository.findAllByUserId(user.getId());
        return categories.stream().
                map(category -> new CategoryResponse(category.getId(), category.getName())).
                toList();
    }
}
