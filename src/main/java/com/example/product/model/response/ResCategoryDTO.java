package com.example.product.model.response;

import java.time.LocalDateTime;

import com.example.product.entity.Category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResCategoryDTO {
    private Long id;
    private String name;
    private String description;
    private Category parentCategory;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
}
