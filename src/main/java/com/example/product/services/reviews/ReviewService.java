package com.example.product.services.reviews;

import com.example.product.models.request.managers.ReqReviewDTO;
import com.example.product.models.response.managers.ResReviewDTO;

import java.util.List;

public interface ReviewService {
    ResReviewDTO createReview(ReqReviewDTO dto);

    ResReviewDTO updateReview(Long id, ReqReviewDTO dto);

    void deleteReview(Long id);

    ResReviewDTO getReviewById(Long id);

    List<ResReviewDTO> getAllReviews();

    List<ResReviewDTO> getReviewsByProductId(Long productId);

    List<ResReviewDTO> getReviewsByCustomerId(Long customerId);
}
