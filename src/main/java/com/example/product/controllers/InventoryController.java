package com.example.product.controllers;

import com.example.product.exceptions.errors.IdInvalidException;
import com.example.product.models.request.managers.ReqInventoryDTO;
import com.example.product.models.response.managers.ResInventoryDTO;
import com.example.product.services.inventories.InventoryService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inventories")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping
    public ResponseEntity<ResInventoryDTO> createInventory(@RequestBody ReqInventoryDTO dto) throws IdInvalidException {
        ResInventoryDTO created = inventoryService.create(dto);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResInventoryDTO> updateInventory(@PathVariable Long id, @RequestBody ReqInventoryDTO dto)
            throws IdInvalidException {
        ResInventoryDTO updated = inventoryService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInventory(@PathVariable Long id) throws IdInvalidException {
        inventoryService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResInventoryDTO> getInventoryById(@PathVariable Long id) throws IdInvalidException {
        ResInventoryDTO inventory = inventoryService.getById(id);
        return ResponseEntity.ok(inventory);
    }

    @GetMapping
    public ResponseEntity<List<ResInventoryDTO>> getAllInventories() throws IdInvalidException {
        List<ResInventoryDTO> inventories = inventoryService.getAll();
        return ResponseEntity.ok(inventories);
    }
}
