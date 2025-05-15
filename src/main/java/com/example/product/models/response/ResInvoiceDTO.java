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
public class ResInvoiceDTO {
    private Long id;
    private String invoiceNumber;
    private LocalDateTime issuedDate;
    private Order order;
    private BigDecimal totalAmount;
    private BigDecimal vat;
    private BigDecimal discount;
    private String paymentMethod;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
}