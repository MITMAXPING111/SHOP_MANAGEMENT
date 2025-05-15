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
public class ReqAddressDTO {
    private String street;
    private String city;
    private String state;
    private String zipCode;
    private String country;
    private Long customerId; // dùng để gán customer trong xử lý service

    private String type; // SHIPPING, BILLING

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
}
