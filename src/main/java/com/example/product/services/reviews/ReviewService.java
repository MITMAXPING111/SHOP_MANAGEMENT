package com.example.product.services.reviews;

import java.util.List;

import com.example.product.models.request.ReqReviewDTO;
import com.example.product.models.response.ResReviewDTO;

public interface ReviewService {
    ResReviewDTO createReview(ReqReviewDTO dto);

    ResReviewDTO updateReview(Long id, ReqReviewDTO dto);

    void deleteReview(Long id);

    ResReviewDTO getReviewById(Long id);

    List<ResReviewDTO> getAllReviews();
}
