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
public class ReqOrderItemDTO {
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;

    private Long orderId;
    private List<Long> productVariantIds;

    private String createdBy;
    private LocalDateTime createdAt;
    private String updatedBy;
    private LocalDateTime updatedAt;
}
