package com.example.product.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.product.entities.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
