package com.example.product.controllers;

import com.example.product.models.request.products.ReqProductVariantDTO;
import com.example.product.models.response.products.ResProductVariantDTO;
import com.example.product.services.productVariant.ProductVariantService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product-variants")
@RequiredArgsConstructor
public class ProductVariantController {

    private final ProductVariantService productVariantService;

    @PostMapping
    public ResponseEntity<ResProductVariantDTO> create(@RequestBody ReqProductVariantDTO request) {
        ResProductVariantDTO response = productVariantService.create(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResProductVariantDTO> getById(@PathVariable Long id) {
        ResProductVariantDTO response = productVariantService.getById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ResProductVariantDTO>> getAll() {
        List<ResProductVariantDTO> responseList = productVariantService.getAll();
        return ResponseEntity.ok(responseList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResProductVariantDTO> update(
            @PathVariable Long id,
            @RequestBody ReqProductVariantDTO request) {
        ResProductVariantDTO response = productVariantService.update(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productVariantService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
