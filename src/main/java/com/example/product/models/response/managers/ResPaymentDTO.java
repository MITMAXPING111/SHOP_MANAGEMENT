package com.example.product.models.response.managers;

import com.example.product.constants.PaymentMethod;
import com.example.product.constants.StatusPayment;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResPaymentDTO {
    private Long id;
    private LocalDateTime paymentDate;
    private PaymentMethod paymentMethod;
    private BigDecimal amount;
    private String transactionId;
    private StatusPayment status; // SUCCESS, FAILED, PENDING

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;

    private List<Long> orderIds;
}
