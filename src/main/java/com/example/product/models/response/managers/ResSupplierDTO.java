package com.example.product.models.response.managers;

import java.time.LocalDateTime;
import java.util.List;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResSupplierDTO {
    private Long id;

    private String name;
    private String contactName;
    private String phone;
    private String email;

    private String province;
    private String ward;
    private String addressDetail;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;

    private List<Long> productIds;
}
