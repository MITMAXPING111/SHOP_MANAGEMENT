package com.example.product.controllers;

import com.example.product.models.request.ReqCategoryDTO;
import com.example.product.models.response.ResCategoryDTO;
import com.example.product.services.categories.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<ResCategoryDTO> createCategory(@RequestBody ReqCategoryDTO dto) {
        ResCategoryDTO createdCategory = categoryService.createCategory(dto);
        return ResponseEntity.ok(createdCategory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResCategoryDTO> updateCategory(@PathVariable Long id,
            @RequestBody ReqCategoryDTO dto) {
        ResCategoryDTO updatedCategory = categoryService.updateCategory(id, dto);
        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResCategoryDTO> getCategoryById(@PathVariable Long id) {
        ResCategoryDTO category = categoryService.getCategoryById(id);
        return ResponseEntity.ok(category);
    }

    @GetMapping
    public ResponseEntity<List<ResCategoryDTO>> getAllCategories() {
        List<ResCategoryDTO> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }
}
