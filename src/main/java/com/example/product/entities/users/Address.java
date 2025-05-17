package com.example.product.entities.users;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.example.product.constants.AddressType;
import com.example.product.entities.products.Order;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TBL_ADDRESSES")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String province; // Tỉnh/Thành phố
    private String ward; // Phường/Xã
    @Lob
    private String addressDetail; // Số nhà, tên đường

    @Enumerated(EnumType.STRING)
    private AddressType type; // SHIPPING, BILLING

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;

    @ManyToMany(mappedBy = "addresses")
    private Set<Customer> customers = new HashSet<>();

    @OneToMany(mappedBy = "address", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();
}
