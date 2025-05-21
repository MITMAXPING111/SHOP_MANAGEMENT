package com.example.product.entities.users;

import java.time.LocalDateTime;

import com.example.product.utils.JwtService;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TBL_ACCOUNT_IMAGES")
public class AccountImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String url_image;
    private String id_image;
    private String id_folder;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    @OneToOne
    @JoinColumn(name = "customer_id", unique = true)
    private Customer customer;

    private String createdBy;
    private LocalDateTime createdAt;
    private String updatedBy;
    private LocalDateTime updatedAt;

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
