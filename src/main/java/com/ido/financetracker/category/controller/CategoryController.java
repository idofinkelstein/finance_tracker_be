package com.ido.financetracker.category.controller;

import com.ido.financetracker.category.dto.CategoryRequest;
import com.ido.financetracker.category.dto.CategoryResponse;
import com.ido.financetracker.category.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getCategory(@PathVariable Long id) {

        CategoryResponse categoryResponse = categoryService.getCategory(id);

        return new ResponseEntity<>(categoryResponse, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {

        List<CategoryResponse> categoryResponses = categoryService.getAll();

        return new ResponseEntity<>(categoryResponses, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> postCategory(@Valid @RequestBody CategoryRequest categoryRequest) {

        CategoryResponse categoryResponse = categoryService.postCategory(categoryRequest);

        return new ResponseEntity<>(categoryResponse, HttpStatus.CREATED);
    }
}
