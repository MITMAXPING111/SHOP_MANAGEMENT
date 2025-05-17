package com.example.product.controllers;

import com.example.product.models.request.managers.ReqDiscountDTO;
import com.example.product.models.response.managers.ResDiscountDTO;
import com.example.product.services.discounts.DiscountService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/discounts")
@RequiredArgsConstructor
public class DiscountController {

    private final DiscountService discountService;

    @PostMapping
    public ResponseEntity<ResDiscountDTO> createDiscount(@RequestBody ReqDiscountDTO dto) {
        ResDiscountDTO created = discountService.createDiscount(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResDiscountDTO> updateDiscount(@PathVariable Long id, @RequestBody ReqDiscountDTO dto) {
        ResDiscountDTO updated = discountService.updateDiscount(id, dto);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResDiscountDTO> getDiscountById(@PathVariable Long id) {
        ResDiscountDTO dto = discountService.getDiscountById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<ResDiscountDTO>> getAllDiscounts() {
        List<ResDiscountDTO> list = discountService.getAllDiscounts();
        return ResponseEntity.ok(list);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDiscount(@PathVariable Long id) {
        discountService.deleteDiscount(id);
        return ResponseEntity.noContent().build();
    }
}
