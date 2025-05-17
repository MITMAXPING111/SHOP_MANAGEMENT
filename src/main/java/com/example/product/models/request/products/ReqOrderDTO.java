package com.example.product.models.request.products;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReqOrderDTO {
    private BigDecimal totalAmount;
    private Long addressId;
    private Long customerId;
    private Long paymentId;

    private List<ReqOrderItemDTO> orderItems;

    private String createdBy;
    private LocalDateTime createdAt;
    private String updatedBy;
    private LocalDateTime updatedAt;
}
