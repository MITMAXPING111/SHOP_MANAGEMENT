package com.example.product.services.inventories;

import com.example.product.entities.managers.Inventory;
import com.example.product.entities.products.ProductVariant;
import com.example.product.exceptions.errors.IdInvalidException;
import com.example.product.models.request.managers.ReqInventoryDTO;
import com.example.product.models.response.managers.ResInventoryDTO;
import com.example.product.repositories.InventoryRepository;
import com.example.product.repositories.ProductVariantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final ProductVariantRepository productVariantRepository;

    @Override
    public ResInventoryDTO create(ReqInventoryDTO dto) throws IdInvalidException {
        Inventory inventory = new Inventory();
        inventory.setQuantityInStock(dto.getQuantityInStock());
        inventory.setQuantityReserved(dto.getQuantityReserved());
        inventory.setQuantityAvailable(dto.getQuantityAvailable());
        inventory.setMinimumQuantityThreshold(dto.getMinimumQuantityThreshold());

        inventory.setCreatedBy(dto.getCreatedBy());
        inventory.setCreatedAt(LocalDateTime.now());

        ProductVariant productVariant = productVariantRepository.findById(dto.getProductVariantId())
                .orElseThrow(() -> new IdInvalidException(
                        "ProductVariant not found with ID: " + dto.getProductVariantId()));
        inventory.setProductVariant(productVariant);

        Inventory saved = inventoryRepository.save(inventory);
        return mapToDTO(saved);
    }

    @Override
    public ResInventoryDTO update(Long id, ReqInventoryDTO dto) throws IdInvalidException {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new IdInvalidException("Inventory not found with ID: " + id));

        inventory.setQuantityInStock(dto.getQuantityInStock());
        inventory.setQuantityReserved(dto.getQuantityReserved());
        inventory.setQuantityAvailable(dto.getQuantityAvailable());
        inventory.setMinimumQuantityThreshold(dto.getMinimumQuantityThreshold());

        inventory.setUpdatedBy(dto.getUpdatedBy());
        inventory.setUpdatedAt(LocalDateTime.now());

        Inventory updated = inventoryRepository.save(inventory);
        return mapToDTO(updated);
    }

    @Override
    public void delete(Long id) throws IdInvalidException {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new IdInvalidException("Inventory not found with ID: " + id));
        inventoryRepository.delete(inventory);
    }

    @Override
    public ResInventoryDTO getById(Long id) throws IdInvalidException {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new IdInvalidException("Inventory not found with ID: " + id));
        return mapToDTO(inventory);
    }

    @Override
    public List<ResInventoryDTO> getAll() {
        return inventoryRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private ResInventoryDTO mapToDTO(Inventory inventory) {
        return ResInventoryDTO.builder()
                .id(inventory.getId())
                .quantityInStock(inventory.getQuantityInStock())
                .quantityReserved(inventory.getQuantityReserved())
                .quantityAvailable(inventory.getQuantityAvailable())
                .minimumQuantityThreshold(inventory.getMinimumQuantityThreshold())
                .createdAt(inventory.getCreatedAt())
                .createdBy(inventory.getCreatedBy())
                .updatedAt(inventory.getUpdatedAt())
                .updatedBy(inventory.getUpdatedBy())
                .productVariantId(inventory.getProductVariant() != null ? inventory.getProductVariant().getId() : null)
                .build();
    }
}
