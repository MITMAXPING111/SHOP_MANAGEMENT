package com.example.product.models.response.managers;

import com.example.product.constants.PaymentMethod;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResInvoiceDTO {
    private Long id;
    private String invoiceNumber;
    private LocalDateTime issuedDate;

    private BigDecimal totalAmount;
    private BigDecimal vat;
    private BigDecimal discount;

    private PaymentMethod paymentMethod;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;

    private Long orderId;
}