package com.example.product.controllers;

import com.example.product.models.request.managers.ReqSupplierDTO;
import com.example.product.models.response.managers.ResSupplierDTO;
import com.example.product.services.suppliers.SupplierService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/suppliers")
@RequiredArgsConstructor
public class SupplierController {

    private final SupplierService supplierService;

    @PostMapping
    public ResponseEntity<ResSupplierDTO> createSupplier(@RequestBody ReqSupplierDTO dto) {
        ResSupplierDTO createdSupplier = supplierService.createSupplier(dto);
        return ResponseEntity.ok(createdSupplier);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResSupplierDTO> updateSupplier(
            @PathVariable Long id,
            @RequestBody ReqSupplierDTO dto) {
        ResSupplierDTO updatedSupplier = supplierService.updateSupplier(id, dto);
        return ResponseEntity.ok(updatedSupplier);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSupplier(@PathVariable Long id) {
        supplierService.deleteSupplier(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResSupplierDTO> getSupplierById(@PathVariable Long id) {
        ResSupplierDTO supplier = supplierService.getSupplierById(id);
        return ResponseEntity.ok(supplier);
    }

    @GetMapping
    public ResponseEntity<List<ResSupplierDTO>> getAllSuppliers() {
        List<ResSupplierDTO> suppliers = supplierService.getAllSuppliers();
        return ResponseEntity.ok(suppliers);
    }
}
