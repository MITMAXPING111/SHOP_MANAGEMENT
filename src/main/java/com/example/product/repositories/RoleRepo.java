package com.example.product.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.product.entities.users.Role;

@Repository
public interface RoleRepo extends JpaRepository<Role, Integer> {
}
