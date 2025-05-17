package com.example.product.models.request.managers;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReqDiscountDTO {
    private String code;
    private String description;
    private BigDecimal percentage;
    private BigDecimal maxAmount;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Boolean active;

    // IDs của các ProductVariant liên quan
    private Set<Long> productVariantIds;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
}
