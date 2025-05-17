package com.example.product.models.request.managers;

import java.time.LocalDateTime;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReqReviewDTO {
    private Integer rating = 5; // Số sao đánh giá (1–5)
    private String comment; // Nội dung bình luận
    private Long customerId; // ID khách hàng
    private Long productId; // ID sản phẩm

    private String createdBy;
    private LocalDateTime createdAt;
    private String updatedBy;
    private LocalDateTime updatedAt;
}