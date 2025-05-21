package com.example.product.entities.managers;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.product.constants.PaymentMethod;
import com.example.product.entities.products.Order;
import com.example.product.utils.JwtService;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TBL_INVOICES")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String invoiceNumber; // số hóa đơn
    private LocalDateTime issuedDate; // ngày xuất hóa đơn

    private BigDecimal totalAmount;
    private BigDecimal vat = new BigDecimal("10"); // 10% mặc định
    private BigDecimal discount;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod; // COD, CREDIT_CARD, BANK_TRANSFER

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
