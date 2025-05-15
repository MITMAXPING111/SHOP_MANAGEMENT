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
public class ReqInvoiceDTO {
    private String invoiceNumber;
    private LocalDateTime issuedDate;
    private Long orderId;
    private BigDecimal totalAmount;
    private BigDecimal vat;
    private BigDecimal discount;
    private String paymentMethod;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
}
