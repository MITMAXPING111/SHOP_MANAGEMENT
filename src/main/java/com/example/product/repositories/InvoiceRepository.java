package com.example.product.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.product.entities.managers.Invoice;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
}
