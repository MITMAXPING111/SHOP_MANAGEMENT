package com.example.product.entities.managers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.product.entities.products.Product;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "TBL_SUPPLIERS")
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String name;
    @Lob
    private String contactName;
    private String phone;
    private String email;

    private String province; // Tỉnh/Thành phố
    private String ward; // Phường/Xã
    @Lob
    private String addressDetail; // Số nhà, tên đường

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;

    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> products = new ArrayList<>();
}
