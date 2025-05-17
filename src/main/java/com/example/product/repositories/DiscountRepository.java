package com.example.product.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.product.entities.managers.Discount;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Long> {
    Discount findByCode(String code);
}
