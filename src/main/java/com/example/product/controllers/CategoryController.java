package com.example.product.controllers;

import com.example.product.models.request.products.ReqCategoryDTO;
import com.example.product.models.response.products.ResCategoryDTO;
import com.example.product.services.categories.CategoryService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<ResCategoryDTO> createCategory(@RequestBody ReqCategoryDTO reqCategoryDTO) {
        ResCategoryDTO createdCategory = categoryService.createCategory(reqCategoryDTO);
        return ResponseEntity.ok(createdCategory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResCategoryDTO> updateCategory(
            @PathVariable("id") Long id,
            @RequestBody ReqCategoryDTO reqCategoryDTO) {
        ResCategoryDTO updatedCategory = categoryService.updateCategory(id, reqCategoryDTO);
        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable("id") Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResCategoryDTO> getCategoryById(@PathVariable("id") Long id) {
        ResCategoryDTO category = categoryService.getCategoryById(id);
        return ResponseEntity.ok(category);
    }

    @GetMapping
    public ResponseEntity<List<ResCategoryDTO>> getAllCategories() {
        List<ResCategoryDTO> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }
}
