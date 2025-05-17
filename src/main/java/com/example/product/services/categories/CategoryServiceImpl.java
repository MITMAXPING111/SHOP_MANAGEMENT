package com.example.product.services.categories;

import com.example.product.entities.products.Category;
import com.example.product.entities.products.Product;
import com.example.product.models.request.products.ReqCategoryDTO;
import com.example.product.models.response.products.ResCategoryDTO;
import com.example.product.repositories.CategoryRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public ResCategoryDTO createCategory(ReqCategoryDTO reqCategoryDTO) {
        Category category = new Category();
        BeanUtils.copyProperties(reqCategoryDTO, category);
        category.setCreatedAt(LocalDateTime.now());
        category.setUpdatedAt(LocalDateTime.now());

        Category savedCategory = categoryRepository.save(category);
        return mapToDTO(savedCategory);
    }

    @Override
    public ResCategoryDTO updateCategory(Long categoryId, ReqCategoryDTO reqCategoryDTO) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with ID: " + categoryId));

        category.setName(reqCategoryDTO.getName());
        category.setDescription(reqCategoryDTO.getDescription());
        category.setUpdatedBy(reqCategoryDTO.getUpdatedBy());
        category.setUpdatedAt(LocalDateTime.now());

        Category updatedCategory = categoryRepository.save(category);
        return mapToDTO(updatedCategory);
    }

    @Override
    public void deleteCategory(Long categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new EntityNotFoundException("Category not found with ID: " + categoryId);
        }
        categoryRepository.deleteById(categoryId);
    }

    @Override
    public ResCategoryDTO getCategoryById(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with ID: " + categoryId));
        return mapToDTO(category);
    }

    @Override
    public List<ResCategoryDTO> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private ResCategoryDTO mapToDTO(Category category) {
        List<Long> productIds = category.getProducts() != null
                ? category.getProducts().stream()
                        .map(Product::getId)
                        .collect(Collectors.toList())
                : List.of();

        return ResCategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .createdAt(category.getCreatedAt())
                .updatedAt(category.getUpdatedAt())
                .createdBy(category.getCreatedBy())
                .updatedBy(category.getUpdatedBy())
                .productIds(productIds)
                .build();
    }
}
