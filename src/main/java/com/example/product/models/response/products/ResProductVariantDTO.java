package com.example.product.models.response.products;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResProductVariantDTO {
    private Long id;
    private String name;
    private String description;
    private Integer totalQuantity;
    private BigDecimal minPrice;
    private String sku;
    private Boolean active;

    private Set<ResProductAttributeValueDTO> productAttributeValues;

    private Set<Long> productAttributeValueIds = new HashSet<>();
    private Long productId;
    private Long orderItemId;
    private Set<Long> discountIds = new HashSet<>();
    private Long inventoryId;

    private String createdBy;
    private LocalDateTime createdAt;
    private String updatedBy;
    private LocalDateTime updatedAt;
}