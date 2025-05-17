package com.example.product.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.product.entities.users.AccountImage;

@Repository
public interface AccountImageRepository extends JpaRepository<AccountImage, Long> {

}
