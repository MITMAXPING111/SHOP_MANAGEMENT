package com.example.product.services.reviews;

import com.example.product.entities.managers.Review;
import com.example.product.entities.products.Product;
import com.example.product.entities.users.Customer;
import com.example.product.models.request.managers.ReqReviewDTO;
import com.example.product.models.response.managers.ResReviewDTO;
import com.example.product.repositories.CustomerRepository;
import com.example.product.repositories.ProductRepository;
import com.example.product.repositories.ReviewRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;

    @Override
    public ResReviewDTO createReview(ReqReviewDTO dto) {
        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        Customer customer = customerRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));

        Review review = new Review();
        review.setRating(dto.getRating());
        review.setComment(dto.getComment());
        review.setCreatedAt(dto.getCreatedAt());
        review.setUpdatedAt(dto.getUpdatedAt());
        review.setCreatedBy(dto.getCreatedBy());
        review.setUpdatedBy(dto.getUpdatedBy());
        review.setReviewDate(dto.getCreatedAt());

        review.setProduct(product);
        review.setCustomer(customer);

        Review saved = reviewRepository.save(review);
        return mapToDTO(saved);
    }

    @Override
    public ResReviewDTO updateReview(Long id, ReqReviewDTO dto) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Review not found"));

        review.setRating(dto.getRating());
        review.setComment(dto.getComment());
        review.setUpdatedAt(dto.getUpdatedAt());
        review.setUpdatedBy(dto.getUpdatedBy());

        Review saved = reviewRepository.save(review);
        return mapToDTO(saved);
    }

    @Override
    public void deleteReview(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Review not found"));
        reviewRepository.delete(review);
    }

    @Override
    public ResReviewDTO getReviewById(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Review not found"));
        return mapToDTO(review);
    }

    @Override
    public List<ResReviewDTO> getAllReviews() {
        return reviewRepository.findAll()
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<ResReviewDTO> getReviewsByProductId(Long productId) {
        return reviewRepository.findByProductId(productId)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<ResReviewDTO> getReviewsByCustomerId(Long customerId) {
        return reviewRepository.findByCustomerId(customerId)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    private ResReviewDTO mapToDTO(Review review) {
        return ResReviewDTO.builder()
                .id(review.getId())
                .rating(review.getRating())
                .comment(review.getComment())
                .reviewDate(review.getReviewDate())
                .createdAt(review.getCreatedAt())
                .updatedAt(review.getUpdatedAt())
                .createdBy(review.getCreatedBy())
                .updatedBy(review.getUpdatedBy())
                .customerId(review.getCustomer().getId())
                .customerName(review.getCustomer().getName())
                .productId(review.getProduct().getId())
                .productName(review.getProduct().getName())
                .build();
    }
}
