package com.example.product.models.response.managers;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

import com.example.product.models.response.products.ResProductVariantDTO;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResDiscountDTO {
    private Long id;
    private String code;
    private String description;
    private BigDecimal percentage;
    private BigDecimal maxAmount;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;

    // Thay ProductVariantResponse bằng DTO thực tế bạn dùng
    private Set<ResProductVariantDTO> productVariantDTOs;
}