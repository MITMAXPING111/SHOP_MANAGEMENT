package com.example.product.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.product.entities.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
