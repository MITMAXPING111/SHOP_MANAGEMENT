package com.example.product.models.request;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReqPaymentDTO {
    private LocalDateTime paymentDate;
    private String paymentMethod;
    private BigDecimal amount;
    private Long orderId;
    private String transactionId;
    private String status; // SUCCESS, FAILED, PENDING

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
}