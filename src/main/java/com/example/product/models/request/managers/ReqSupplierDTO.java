package com.example.product.models.request.managers;

import java.time.LocalDateTime;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReqSupplierDTO {
    private String name;
    private String contactName;
    private String phone;
    private String email;

    private String province;
    private String ward;
    private String addressDetail;

    private String createdBy;
    private LocalDateTime createdAt;
    private String updatedBy;
    private LocalDateTime updatedAt;
}
