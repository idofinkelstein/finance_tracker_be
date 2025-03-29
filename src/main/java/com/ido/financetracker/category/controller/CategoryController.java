package com.ido.financetracker.category.controller;

import com.ido.financetracker.category.dto.CategoryRequest;
import com.ido.financetracker.category.dto.CategoryResponse;
import com.ido.financetracker.category.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // Implement CRUD operations for Category entity
    @GetMapping
    public ResponseEntity<CategoryResponse> getCategory(@RequestBody CategoryRequest categoryRequest) {

        CategoryResponse categoryResponse = categoryService.getCategory(categoryRequest.name());

        return new ResponseEntity<>(categoryResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> postCategory(@RequestBody CategoryRequest categoryRequest) {

        CategoryResponse categoryResponse = categoryService.postCategory(categoryRequest);

        return new ResponseEntity<>(categoryResponse, HttpStatus.CREATED);
    }
}
