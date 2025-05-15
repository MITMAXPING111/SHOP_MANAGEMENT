package com.example.product.entities;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TBL_INVENTORIES")
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer quantityInStock; // slg trong kho
    private Integer quantityReserved; // slg đặt nhưng chưa giao
    private Integer quantityAvailable; // slg sẵn sàng để bán
    private Integer minimumQuantityThreshold; // Số lượng tối thiểu trước khi cảnh báo

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;

    @OneToOne
    @JoinColumn(name = "product_variant_id", unique = true)
    private ProductVariant productVariant;
}
