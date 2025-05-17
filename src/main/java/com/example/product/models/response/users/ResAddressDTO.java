package com.example.product.models.response.users;

import java.time.LocalDateTime;

import com.example.product.constants.AddressType;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResAddressDTO {
    private Long id;
    private String province;
    private String ward;
    private String addressDetail;
    private AddressType type;

    private String createdBy;
    private LocalDateTime createdAt;
    private String updatedBy;
    private LocalDateTime updatedAt;
}
