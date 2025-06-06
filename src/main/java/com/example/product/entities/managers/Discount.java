package com.example.product.entities.managers;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.example.product.entities.products.ProductVariant;
import com.example.product.utils.JwtService;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TBL_DISCOUNTS")
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;
    @Lob
    private String description;
    private BigDecimal percentage;
    private BigDecimal maxAmount;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    // mặc định là false, khi nào xài thì enable
    private Boolean active = false;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;

    // discount phải gắn với sp cụ thể
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "discount_variant", joinColumns = @JoinColumn(name = "discount_id"), inverseJoinColumns = @JoinColumn(name = "variant_id"))
    private Set<ProductVariant> productVariants = new HashSet<>();

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
