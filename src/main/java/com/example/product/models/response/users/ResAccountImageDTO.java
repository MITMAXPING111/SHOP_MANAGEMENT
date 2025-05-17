package com.example.product.models.response.users;

import java.time.LocalDateTime;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResAccountImageDTO {
    private Long id;

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
