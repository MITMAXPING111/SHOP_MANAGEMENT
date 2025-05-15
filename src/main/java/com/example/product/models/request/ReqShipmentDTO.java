package com.example.product.models.request;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReqShipmentDTO {
    private String trackingNumber;
    private String carrier;
    private LocalDateTime shippedDate;
    private LocalDateTime deliveredDate;
    private Long orderId;
    private String status; // PENDING, SHIPPED, DELIVERED, RETURNED

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
}
