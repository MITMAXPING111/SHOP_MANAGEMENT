package com.example.product.controllers;

import com.example.product.models.request.products.ReqProductAttributeDTO;
import com.example.product.models.response.products.ResProductAttributeDTO;
import com.example.product.services.productAttribute.ProductAttributeService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product-attributes")
@RequiredArgsConstructor
public class ProductAttributeController {

    private final ProductAttributeService productAttributeService;

    @PostMapping
    public ResponseEntity<ResProductAttributeDTO> createProductAttribute(@RequestBody ReqProductAttributeDTO req) {
        ResProductAttributeDTO created = productAttributeService.createProductAttribute(req);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResProductAttributeDTO> updateProductAttribute(
            @PathVariable Long id,
            @RequestBody ReqProductAttributeDTO req) {
        ResProductAttributeDTO updated = productAttributeService.updateProductAttribute(id, req);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResProductAttributeDTO> getProductAttributeById(@PathVariable Long id) {
        ResProductAttributeDTO dto = productAttributeService.getProductAttributeById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<ResProductAttributeDTO>> getAllProductAttributes() {
        List<ResProductAttributeDTO> list = productAttributeService.getAllProductAttributes();
        return ResponseEntity.ok(list);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductAttribute(@PathVariable Long id) {
        productAttributeService.deleteProductAttribute(id);
        return ResponseEntity.noContent().build();
    }
}
