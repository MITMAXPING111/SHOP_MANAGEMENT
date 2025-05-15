package com.example.product.models.response;

import java.time.LocalDateTime;

import com.example.product.entities.Product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResInventoryDTO {
    private Long id;
    private Product product;
    private Integer quantityInStock;
    private Integer quantityReserved;
    private Integer quantityAvailable;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
}
