package com.example.product.models.response.products;

import com.example.product.constants.StatusOrder;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResOrderDTO {
    private Long id;
    private StatusOrder status;
    private BigDecimal totalAmount;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;

    private Long addressId;
    private Long customerId;
    private Long paymentId;
    private Long invoiceId;
    private Long shipmentId;

    private List<ResOrderItemDTO> orderItems;
}
