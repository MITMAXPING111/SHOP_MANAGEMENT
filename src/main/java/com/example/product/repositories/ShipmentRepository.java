package com.example.product.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.product.entities.Shipment;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
}
