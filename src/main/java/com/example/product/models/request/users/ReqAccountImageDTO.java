package com.example.product.models.request.users;

import java.time.LocalDateTime;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReqAccountImageDTO {
    private String urlImage;
    private String idImage;
    private String idFolder;

    private Long userId;
    private Long customerId;

    private String createdBy;
    private LocalDateTime createdAt;
    private String updatedBy;
    private LocalDateTime updatedAt;
}