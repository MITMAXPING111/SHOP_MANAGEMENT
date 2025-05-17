package com.example.product.models.request.users;

import java.time.LocalDateTime;

import com.example.product.constants.AddressType;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReqAddressDTO {
    private String province; // Tỉnh/Thành phố
    private String ward; // Phường/Xã
    private String addressDetail; // Số nhà, tên đường
    private AddressType type; // SHIPPING, BILLING

    private String createdBy;
    private LocalDateTime createdAt;
    private String updatedBy;
    private LocalDateTime updatedAt;
}
