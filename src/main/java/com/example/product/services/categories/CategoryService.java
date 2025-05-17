package com.example.product.services.categories;

import com.example.product.models.request.products.ReqCategoryDTO;
import com.example.product.models.response.products.ResCategoryDTO;

import java.util.List;

public interface CategoryService {
    ResCategoryDTO createCategory(ReqCategoryDTO reqCategoryDTO);

    ResCategoryDTO updateCategory(Long categoryId, ReqCategoryDTO reqCategoryDTO);

    void deleteCategory(Long categoryId);

    ResCategoryDTO getCategoryById(Long categoryId);

    List<ResCategoryDTO> getAllCategories();
}
