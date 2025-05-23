package com.example.product.entities.managers;

import java.time.LocalDateTime;

import com.example.product.constants.StatusShipment;
import com.example.product.entities.products.Order;
import com.example.product.utils.JwtService;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TBL_SHIPMENTS")
public class Shipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String trackingNumber; // mã đơn
    private String carrier; // người vận chuyển
    private LocalDateTime shippedDate;
    private LocalDateTime deliveredDate;

    @Enumerated(EnumType.STRING)
    private StatusShipment status = StatusShipment.PENDING; // PENDING, SHIPPED, DELIVERED, RETURNED

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;

    @OneToOne
    @JoinColumn(name = "order_id", unique = true)
    private Order order;

    @PrePersist
    public void handleBeforeCreate() {
        this.createdBy = JwtService.getCurrentUserLogin().isPresent() == true
                ? JwtService.getCurrentUserLogin().get()
                : "";

        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void handleBeforeUpdate() {
        this.updatedBy = JwtService.getCurrentUserLogin().isPresent() == true
                ? JwtService.getCurrentUserLogin().get()
                : "";

        this.updatedAt = LocalDateTime.now();
    }
}
