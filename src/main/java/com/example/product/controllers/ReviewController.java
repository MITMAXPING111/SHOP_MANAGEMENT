package com.example.product.controllers;

import com.example.product.models.request.managers.ReqReviewDTO;
import com.example.product.models.response.managers.ResReviewDTO;
import com.example.product.services.reviews.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ResReviewDTO> createReview(@RequestBody ReqReviewDTO dto) {
        ResReviewDTO created = reviewService.createReview(dto);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResReviewDTO> updateReview(@PathVariable Long id, @RequestBody ReqReviewDTO dto) {
        ResReviewDTO updated = reviewService.updateReview(id, dto);
        return ResponseEntity.ok(updated);
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

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ResReviewDTO>> getReviewsByProductId(@PathVariable Long productId) {
        List<ResReviewDTO> reviews = reviewService.getReviewsByProductId(productId);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<ResReviewDTO>> getReviewsByCustomerId(@PathVariable Long customerId) {
        List<ResReviewDTO> reviews = reviewService.getReviewsByCustomerId(customerId);
        return ResponseEntity.ok(reviews);
    }
}
