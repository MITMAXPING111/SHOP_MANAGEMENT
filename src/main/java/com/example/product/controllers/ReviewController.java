package com.example.product.controllers;

import com.example.product.models.request.ReqReviewDTO;
import com.example.product.models.response.ResReviewDTO;
import com.example.product.services.reviews.ReviewService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ResReviewDTO> createReview(@RequestBody ReqReviewDTO dto) {
        ResReviewDTO createdReview = reviewService.createReview(dto);
        return ResponseEntity.ok(createdReview);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResReviewDTO> updateReview(@PathVariable Long id,
            @RequestBody ReqReviewDTO dto) {
        ResReviewDTO updatedReview = reviewService.updateReview(id, dto);
        return ResponseEntity.ok(updatedReview);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResReviewDTO> getReviewById(@PathVariable Long id) {
        ResReviewDTO review = reviewService.getReviewById(id);
        return ResponseEntity.ok(review);
    }

    @GetMapping
    public ResponseEntity<List<ResReviewDTO>> getAllReviews() {
        List<ResReviewDTO> reviews = reviewService.getAllReviews();
        return ResponseEntity.ok(reviews);
    }
}
