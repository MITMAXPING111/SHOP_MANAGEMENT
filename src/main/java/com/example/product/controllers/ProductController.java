package com.example.product.controllers;

import com.example.product.models.request.products.ReqProductDTO;
import com.example.product.models.response.products.ResProductDTO;
import com.example.product.services.products.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ResProductDTO> createProduct(@RequestBody ReqProductDTO reqProductDTO) {
        ResProductDTO createdProduct = productService.createProduct(reqProductDTO);
        return ResponseEntity.ok(createdProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResProductDTO> updateProduct(
            @PathVariable Long id,
            @RequestBody ReqProductDTO reqProductDTO) {
        ResProductDTO updatedProduct = productService.updateProduct(id, reqProductDTO);
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
