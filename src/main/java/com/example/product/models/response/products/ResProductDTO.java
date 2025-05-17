package com.example.product.models.response.products;

import com.example.product.entities.products.Category;
import com.example.product.entities.products.ProductVariant;
import com.example.product.entities.managers.Review;
import com.example.product.entities.managers.Supplier;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResProductDTO {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stockQuantity;
    private String sku;
    private Boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;

    private Category category;
    private Supplier supplier;
    private List<ProductVariant> productVariants;
    private List<Review> reviews;

}
