package com.example.product.models.response.managers;

import java.time.LocalDateTime;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResReviewDTO {
    private Long id;
    private Integer rating;
    private String comment;
    private LocalDateTime reviewDate;

    private String createdBy;
    private LocalDateTime createdAt;
    private String updatedBy;
    private LocalDateTime updatedAt;

    private Long customerId;
    private String customerName; // (tùy chọn nếu bạn muốn hiển thị thêm)
    private Long productId;
    private String productName; // (tùy chọn nếu bạn muốn hiển thị thêm)
}
