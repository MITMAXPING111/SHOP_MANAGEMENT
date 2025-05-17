package com.example.product.controllers;

import com.example.product.models.request.products.ReqProductAttributeValueDTO;
import com.example.product.models.response.products.ResProductAttributeValueDTO;
import com.example.product.services.productAttributeValue.ProductAttributeValueService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product-attribute-values")
@RequiredArgsConstructor
public class ProductAttributeValueController {

    private final ProductAttributeValueService productAttributeValueService;

    @PostMapping
    public ResponseEntity<ResProductAttributeValueDTO> create(
            @RequestBody ReqProductAttributeValueDTO request) {
        ResProductAttributeValueDTO created = productAttributeValueService.create(request);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResProductAttributeValueDTO> update(
            @PathVariable Long id,
            @RequestBody ReqProductAttributeValueDTO request) {
        ResProductAttributeValueDTO updated = productAttributeValueService.update(id, request);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productAttributeValueService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResProductAttributeValueDTO> getById(@PathVariable Long id) {
        ResProductAttributeValueDTO value = productAttributeValueService.getById(id);
        return ResponseEntity.ok(value);
    }

    @GetMapping
    public ResponseEntity<List<ResProductAttributeValueDTO>> getAll() {
        List<ResProductAttributeValueDTO> values = productAttributeValueService.getAll();
        return ResponseEntity.ok(values);
    }
}
