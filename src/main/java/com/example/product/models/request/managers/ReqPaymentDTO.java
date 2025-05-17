package com.example.product.models.request.managers;

import com.example.product.constants.PaymentMethod;
import com.example.product.constants.StatusPayment;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReqPaymentDTO {
    private LocalDateTime paymentDate;
    private PaymentMethod paymentMethod;
    private BigDecimal amount;
    private String transactionId;
    private StatusPayment status; // SUCCESS, FAILED, PENDING

    private String createdBy;
    private LocalDateTime createdAt;
    private String updatedBy;
    private LocalDateTime updatedAt;

}
