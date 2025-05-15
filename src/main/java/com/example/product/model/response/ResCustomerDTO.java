package com.example.product.model.response;

import java.time.LocalDateTime;
import java.util.List;

import com.example.product.entity.Order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResCustomerDTO {
    private Long id;
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private Boolean active;
    private List<Order> orders;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
}