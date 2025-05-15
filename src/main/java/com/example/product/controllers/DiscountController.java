package com.example.product.controllers;

import com.example.product.models.request.ReqDiscountDTO;
import com.example.product.models.response.ResDiscountDTO;
import com.example.product.services.discounts.DiscountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/discounts")
public class DiscountController {

    @Autowired
    private DiscountService discountService;

    @PostMapping
    public ResponseEntity<ResDiscountDTO> createDiscount(@RequestBody ReqDiscountDTO dto) {
        ResDiscountDTO createdDiscount = discountService.createDiscount(dto);
        return ResponseEntity.ok(createdDiscount);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResDiscountDTO> updateDiscount(@PathVariable Long id,
            @RequestBody ReqDiscountDTO dto) {
        ResDiscountDTO updatedDiscount = discountService.updateDiscount(id, dto);
        return ResponseEntity.ok(updatedDiscount);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDiscount(@PathVariable Long id) {
        discountService.deleteDiscount(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResDiscountDTO> getDiscountById(@PathVariable Long id) {
        ResDiscountDTO discount = discountService.getDiscountById(id);
        return ResponseEntity.ok(discount);
    }

    @GetMapping
    public ResponseEntity<List<ResDiscountDTO>> getAllDiscounts() {
        List<ResDiscountDTO> discounts = discountService.getAllDiscounts();
        return ResponseEntity.ok(discounts);
    }
}
