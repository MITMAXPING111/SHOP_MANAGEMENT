package com.example.product.services.inventories;

import com.example.product.entities.Inventory;
import com.example.product.entities.Product;
import com.example.product.models.request.ReqInventoryDTO;
import com.example.product.models.response.ResInventoryDTO;
import com.example.product.repositories.InventoryRepository;
import com.example.product.repositories.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public ResInventoryDTO createInventory(ReqInventoryDTO dto) {
        Inventory inventory = new Inventory();
        inventory.setQuantityInStock(dto.getQuantityInStock());
        inventory.setQuantityReserved(dto.getQuantityReserved());
        inventory.setQuantityAvailable(dto.getQuantityAvailable());
        inventory.setCreatedAt(dto.getCreatedAt() != null ? dto.getCreatedAt() : LocalDateTime.now());
        inventory.setUpdatedAt(dto.getUpdatedAt() != null ? dto.getUpdatedAt() : LocalDateTime.now());
        inventory.setCreatedBy(dto.getCreatedBy());
        inventory.setUpdatedBy(dto.getUpdatedBy());

        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + dto.getProductId()));
        inventory.setProduct(product);

        return mapToResDTO(inventoryRepository.save(inventory));
    }

    @Override
    public ResInventoryDTO updateInventory(Long id, ReqInventoryDTO dto) {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventory not found with ID: " + id));

        inventory.setQuantityInStock(dto.getQuantityInStock());
        inventory.setQuantityReserved(dto.getQuantityReserved());
        inventory.setQuantityAvailable(dto.getQuantityAvailable());
        inventory.setUpdatedAt(LocalDateTime.now());
        inventory.setUpdatedBy(dto.getUpdatedBy());

        if (dto.getProductId() != null) {
            Product product = productRepository.findById(dto.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found with ID: " + dto.getProductId()));
            inventory.setProduct(product);
        }

        return mapToResDTO(inventoryRepository.save(inventory));
    }

    @Override
    public void deleteInventory(Long id) {
        inventoryRepository.deleteById(id);
    }

    @Override
    public ResInventoryDTO getInventoryById(Long id) {
        return inventoryRepository.findById(id)
                .map(this::mapToResDTO)
                .orElseThrow(() -> new RuntimeException("Inventory not found with ID: " + id));
    }

    @Override
    public List<ResInventoryDTO> getAllInventories() {
        return inventoryRepository.findAll()
                .stream()
                .map(this::mapToResDTO)
                .collect(Collectors.toList());
    }

    private ResInventoryDTO mapToResDTO(Inventory inventory) {
        return new ResInventoryDTO(
                inventory.getId(),
                inventory.getProduct(),
                inventory.getQuantityInStock(),
                inventory.getQuantityReserved(),
                inventory.getQuantityAvailable(),
                inventory.getCreatedAt(),
                inventory.getUpdatedAt(),
                inventory.getCreatedBy(),
                inventory.getUpdatedBy());
    }
}