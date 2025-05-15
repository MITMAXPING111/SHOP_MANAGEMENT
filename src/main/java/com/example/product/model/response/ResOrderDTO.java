package com.example.product.model.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.example.product.entity.OrderItem;
import com.example.product.entity.Customer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResOrderDTO {
    private Long id;
    private LocalDateTime orderDate;
    private String status;
    private Customer customer;
    private List<OrderItem> orderItems;
    private String shippingAddress;
    private BigDecimal totalAmount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
}
