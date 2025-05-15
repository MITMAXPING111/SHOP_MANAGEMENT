package com.example.product.models.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.product.entities.Order;
import com.example.product.entities.Product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResOrderItemDTO {
    private Long id;
    private Order order;
    private Product product;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
}
