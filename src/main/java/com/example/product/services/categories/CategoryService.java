package com.example.product.services.categories;

import java.util.List;

import com.example.product.models.request.ReqCategoryDTO;
import com.example.product.models.response.ResCategoryDTO;

public interface CategoryService {
    ResCategoryDTO createCategory(ReqCategoryDTO dto);

    ResCategoryDTO updateCategory(Long id, ReqCategoryDTO dto);

    void deleteCategory(Long id);

    ResCategoryDTO getCategoryById(Long id);

    List<ResCategoryDTO> getAllCategories();
}
