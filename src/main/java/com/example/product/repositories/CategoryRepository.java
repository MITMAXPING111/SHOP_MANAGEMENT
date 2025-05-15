package com.example.product.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.product.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
