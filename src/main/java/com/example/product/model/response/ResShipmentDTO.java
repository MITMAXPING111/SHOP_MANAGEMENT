package com.example.product.model.response;

import java.time.LocalDateTime;

import com.example.product.entity.Order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResShipmentDTO {
    private Long id;
    private String trackingNumber;
    private String carrier;
    private LocalDateTime shippedDate;
    private LocalDateTime deliveredDate;
    private Order order;
    private String status; // PENDING, SHIPPED, DELIVERED, RETURNED
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
}
