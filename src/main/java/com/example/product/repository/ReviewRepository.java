package com.example.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.product.entity.Review;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
}
