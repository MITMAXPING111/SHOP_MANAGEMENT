package com.example.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.product.entity.Inventory;

import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
}
