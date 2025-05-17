package com.example.product.models.request.managers;

import com.example.product.constants.PaymentMethod;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReqInvoiceDTO {
    private String invoiceNumber;
    private LocalDateTime issuedDate;

    private BigDecimal totalAmount;
    private BigDecimal vat;
    private BigDecimal discount;

    private PaymentMethod paymentMethod;

    private Long orderId; // ID của đơn hàng liên kết

    private String createdBy;
    private LocalDateTime createdAt;
    private String updatedBy;
    private LocalDateTime updatedAt;
}
