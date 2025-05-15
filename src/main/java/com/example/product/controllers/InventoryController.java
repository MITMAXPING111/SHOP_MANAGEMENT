package com.example.product.controllers;

import com.example.product.models.request.ReqInventoryDTO;
import com.example.product.models.response.ResInventoryDTO;
import com.example.product.services.inventories.InventoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventories")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @PostMapping
    public ResponseEntity<ResInventoryDTO> createInventory(@RequestBody ReqInventoryDTO dto) {
        ResInventoryDTO createdInventory = inventoryService.createInventory(dto);
        return ResponseEntity.ok(createdInventory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResInventoryDTO> updateInventory(@PathVariable Long id, @RequestBody ReqInventoryDTO dto) {
        ResInventoryDTO updatedInventory = inventoryService.updateInventory(id, dto);
        return ResponseEntity.ok(updatedInventory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInventory(@PathVariable Long id) {
        inventoryService.deleteInventory(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResInventoryDTO> getInventoryById(@PathVariable Long id) {
        ResInventoryDTO inventory = inventoryService.getInventoryById(id);
        return ResponseEntity.ok(inventory);
    }

    @GetMapping
    public ResponseEntity<List<ResInventoryDTO>> getAllInventories() {
        List<ResInventoryDTO> inventories = inventoryService.getAllInventories();
        return ResponseEntity.ok(inventories);
    }
}
