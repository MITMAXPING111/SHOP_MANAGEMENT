package com.example.product.services.reviews;

import com.example.product.entities.Customer;
import com.example.product.entities.Product;
import com.example.product.entities.Review;
import com.example.product.models.request.ReqReviewDTO;
import com.example.product.models.response.ResReviewDTO;
import com.example.product.repositories.CustomerRepository;
import com.example.product.repositories.ProductRepository;
import com.example.product.repositories.ReviewRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {

        @Autowired
        private ReviewRepository reviewRepository;

        @Autowired
        private CustomerRepository customerRepository;

        @Autowired
        private ProductRepository productRepository;

        @Override
        public ResReviewDTO createReview(ReqReviewDTO dto) {
                Customer customer = customerRepository.findById(dto.getCustomerId())
                                .orElseThrow(() -> new RuntimeException(
                                                "Customer not found with ID: " + dto.getCustomerId()));

                Product product = productRepository.findById(dto.getProductId())
                                .orElseThrow(() -> new RuntimeException(
                                                "Product not found with ID: " + dto.getProductId()));

                Review review = new Review();
                review.setCustomer(customer);
                review.setProduct(product);
                review.setRating(dto.getRating());
                review.setComment(dto.getComment());
                review.setReviewDate(dto.getReviewDate() != null ? dto.getReviewDate() : LocalDateTime.now());
                review.setCreatedAt(dto.getCreatedAt() != null ? dto.getCreatedAt() : LocalDateTime.now());
                review.setUpdatedAt(dto.getUpdatedAt() != null ? dto.getUpdatedAt() : LocalDateTime.now());
                review.setCreatedBy(dto.getCreatedBy());
                review.setUpdatedBy(dto.getUpdatedBy());

                return mapToResDTO(reviewRepository.save(review));
        }

        @Override
        public ResReviewDTO updateReview(Long id, ReqReviewDTO dto) {
                Review review = reviewRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("Review not found with ID: " + id));

                Customer customer = customerRepository.findById(dto.getCustomerId())
                                .orElseThrow(() -> new RuntimeException(
                                                "Customer not found with ID: " + dto.getCustomerId()));

                Product product = productRepository.findById(dto.getProductId())
                                .orElseThrow(() -> new RuntimeException(
                                                "Product not found with ID: " + dto.getProductId()));

                review.setCustomer(customer);
                review.setProduct(product);
                review.setRating(dto.getRating());
                review.setComment(dto.getComment());
                review.setReviewDate(dto.getReviewDate());
                review.setUpdatedAt(LocalDateTime.now());
                review.setUpdatedBy(dto.getUpdatedBy());

                return mapToResDTO(reviewRepository.save(review));
        }

        @Override
        public void deleteReview(Long id) {
                reviewRepository.deleteById(id);
        }

        @Override
        public ResReviewDTO getReviewById(Long id) {
                return reviewRepository.findById(id)
                                .map(this::mapToResDTO)
                                .orElseThrow(() -> new RuntimeException("Review not found with ID: " + id));
        }

        @Override
        public List<ResReviewDTO> getAllReviews() {
                return reviewRepository.findAll()
                                .stream()
                                .map(this::mapToResDTO)
                                .collect(Collectors.toList());
        }

        private ResReviewDTO mapToResDTO(Review review) {
                return new ResReviewDTO(
                                review.getId(),
                                review.getCustomer() != null ? review.getCustomer().getId() : null,
                                review.getProduct() != null ? review.getProduct().getId() : null,
                                review.getRating(),
                                review.getComment(),
                                review.getReviewDate(),
                                review.getCreatedAt(),
                                review.getUpdatedAt(),
                                review.getCreatedBy(),
                                review.getUpdatedBy());
        }
}
