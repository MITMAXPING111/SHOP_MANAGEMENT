package com.example.product.controllers;

import com.example.product.models.request.ReqProductDTO;
import com.example.product.models.response.ResProductDTO;
import com.example.product.services.products.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<ResProductDTO> createProduct(@RequestBody ReqProductDTO dto) {
        ResProductDTO createdProduct = productService.createProduct(dto);
        return ResponseEntity.ok(createdProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResProductDTO> updateProduct(@PathVariable Long id,
            @RequestBody ReqProductDTO dto) {
        ResProductDTO updatedProduct = productService.updateProduct(id, dto);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResProductDTO> getProductById(@PathVariable Long id) {
        ResProductDTO product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @GetMapping
    public ResponseEntity<List<ResProductDTO>> getAllProducts() {
        List<ResProductDTO> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }
}
