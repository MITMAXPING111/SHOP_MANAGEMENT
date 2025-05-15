package com.example.product.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.product.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
