package com.example.product.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.product.entities.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
}
