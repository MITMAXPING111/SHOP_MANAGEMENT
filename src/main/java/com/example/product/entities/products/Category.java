package com.example.product.entities.products;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.product.utils.JwtService;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "TBL_CATEGORIES")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @Lob
    private String description;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> products = new ArrayList<>();

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
