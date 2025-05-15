package com.example.product.models.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.product.entities.Order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResPaymentDTO {
    private Long id;
    private LocalDateTime paymentDate;
    private String paymentMethod;
    private BigDecimal amount;
    private Order order;
    private String transactionId;
    private String status; // SUCCESS, FAILED, PENDING
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
}