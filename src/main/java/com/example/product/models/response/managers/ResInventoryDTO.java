package com.example.product.models.response.managers;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResInventoryDTO {
    private Long id;

    private Integer quantityInStock;
    private Integer quantityReserved;
    private Integer quantityAvailable;
    private Integer minimumQuantityThreshold;

    private String createdBy;
    private LocalDateTime createdAt;
    private String updatedBy;
    private LocalDateTime updatedAt;

    private Long productVariantId;
}
