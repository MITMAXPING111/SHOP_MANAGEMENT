package com.example.product.models.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.product.entities.Category;
import com.example.product.entities.Supplier;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResProductDTO {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stockQuantity;
    private String sku;
    private Category category; // hoặc chỉ categoryId nếu muốn trả đơn giản hơn
    private Supplier supplier;
    private Boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
}
