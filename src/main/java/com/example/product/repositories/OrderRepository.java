package com.example.product.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.product.entities.products.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
