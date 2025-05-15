package com.example.product.services.categories;

import com.example.product.entities.Category;
import com.example.product.models.request.ReqCategoryDTO;
import com.example.product.models.response.ResCategoryDTO;
import com.example.product.repositories.CategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public ResCategoryDTO createCategory(ReqCategoryDTO dto) {
        Category category = new Category();
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        category.setCreatedAt(dto.getCreatedAt() != null ? dto.getCreatedAt() : LocalDateTime.now());
        category.setUpdatedAt(dto.getUpdatedAt() != null ? dto.getUpdatedAt() : LocalDateTime.now());
        category.setCreatedBy(dto.getCreatedBy());
        category.setUpdatedBy(dto.getUpdatedBy());

        return mapToResDTO(categoryRepository.save(category));
    }

    @Override
    public ResCategoryDTO updateCategory(Long id, ReqCategoryDTO dto) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with ID: " + id));

        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        category.setUpdatedAt(LocalDateTime.now());
        category.setUpdatedBy(dto.getUpdatedBy());

        return mapToResDTO(categoryRepository.save(category));
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public ResCategoryDTO getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .map(this::mapToResDTO)
                .orElseThrow(() -> new RuntimeException("Category not found with ID: " + id));
    }

    @Override
    public List<ResCategoryDTO> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(this::mapToResDTO)
                .collect(Collectors.toList());
    }

    private ResCategoryDTO mapToResDTO(Category category) {
        return new ResCategoryDTO(
                category.getId(),
                category.getName(),
                category.getDescription(),
                category.getCreatedAt(),
                category.getUpdatedAt(),
                category.getCreatedBy(),
                category.getUpdatedBy());
    }
}
