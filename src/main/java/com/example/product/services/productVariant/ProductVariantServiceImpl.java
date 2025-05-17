package com.example.product.services.productVariant;

import com.example.product.entities.managers.Discount;
import com.example.product.entities.managers.Inventory;
import com.example.product.entities.products.*;
import com.example.product.models.request.products.ReqProductVariantDTO;
import com.example.product.models.response.products.ResProductAttributeValueDTO;
import com.example.product.models.response.products.ResProductVariantDTO;
import com.example.product.repositories.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductVariantServiceImpl implements ProductVariantService {

    private final ProductVariantRepository productVariantRepository;
    private final ProductRepository productRepository;
    private final ProductAttributeValueRepository attributeValueRepository;
    private final DiscountRepository discountRepository;
    private final InventoryRepository inventoryRepository;

    @Override
    public ResProductVariantDTO create(ReqProductVariantDTO request) {
        ProductVariant entity = new ProductVariant();
        entity.setCreatedAt(request.getCreatedAt() != null ? request.getCreatedAt() : LocalDateTime.now());
        entity.setCreatedBy(request.getCreatedBy());

        mapDtoToEntity(request, entity);

        productVariantRepository.save(entity);

        return mapEntityToDto(entity);
    }

    @Override
    public ResProductVariantDTO update(Long id, ReqProductVariantDTO request) {
        ProductVariant entity = productVariantRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ProductVariant not found with id: " + id));

        entity.setUpdatedAt(request.getUpdatedAt() != null ? request.getUpdatedAt() : LocalDateTime.now());
        entity.setUpdatedBy(request.getUpdatedBy());

        mapDtoToEntity(request, entity);

        productVariantRepository.save(entity);

        return mapEntityToDto(entity);
    }

    @Override
    public void delete(Long id) {
        if (!productVariantRepository.existsById(id)) {
            throw new EntityNotFoundException("ProductVariant not found with id: " + id);
        }
        productVariantRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public ResProductVariantDTO getById(Long id) {
        ProductVariant entity = productVariantRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ProductVariant not found with id: " + id));
        return mapEntityToDto(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResProductVariantDTO> getAll() {
        return productVariantRepository.findAll().stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    private void mapDtoToEntity(ReqProductVariantDTO dto, ProductVariant entity) {
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setTotalQuantity(dto.getTotalQuantity());
        entity.setMinPrice(dto.getMinPrice());
        entity.setSku(dto.getSku());
        entity.setActive(dto.getActive() != null ? dto.getActive() : Boolean.TRUE);
        entity.setCreatedBy(dto.getCreatedBy());
        entity.setCreatedAt(dto.getCreatedAt());
        entity.setUpdatedBy(dto.getUpdatedBy());
        entity.setUpdatedAt(dto.getUpdatedAt());

        // Product
        if (dto.getProductId() != null) {
            Product product = productRepository.findById(dto.getProductId())
                    .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + dto.getProductId()));
            entity.setProduct(product);
        } else {
            entity.setProduct(null);
        }

        // OrderItem
        if (dto.getOrderItemId() != null) {
            OrderItem orderItem = new OrderItem();
            orderItem.setId(dto.getOrderItemId()); // Assuming OrderItem exists, or load similarly from repo
            entity.setOrderItem(orderItem);
        } else {
            entity.setOrderItem(null);
        }

        // ProductAttributeValues (ManyToMany)
        if (dto.getProductAttributeValueIds() != null && !dto.getProductAttributeValueIds().isEmpty()) {
            Set<ProductAttributeValue> attributeValues = new HashSet<>(
                    attributeValueRepository.findAllById(dto.getProductAttributeValueIds()));
            entity.setProductAttributeValues(attributeValues);
        } else {
            entity.getProductAttributeValues().clear();
        }

        // Discounts (ManyToMany)
        if (dto.getDiscountIds() != null && !dto.getDiscountIds().isEmpty()) {
            Set<Discount> discounts = new HashSet<>(discountRepository.findAllById(dto.getDiscountIds()));
            entity.setDiscounts(discounts);
        } else {
            entity.getDiscounts().clear();
        }

        // Inventory
        if (dto.getInventoryId() != null) {
            Inventory inventory = inventoryRepository.findById(dto.getInventoryId())
                    .orElseThrow(
                            () -> new EntityNotFoundException("Inventory not found with id: " + dto.getInventoryId()));
            entity.setInventory(inventory);
        } else {
            entity.setInventory(null);
        }
    }

    private ResProductVariantDTO mapEntityToDto(ProductVariant entity) {
        Set<ResProductAttributeValueDTO> attributeValuesDTOs = entity.getProductAttributeValues().stream()
                .map(av -> ResProductAttributeValueDTO.builder()
                        .id(av.getId())
                        .name(av.getName())
                        .productAttributeId(av.getProductAttribute() != null ? av.getProductAttribute().getId() : null)
                        .createdBy(av.getCreatedBy())
                        .createdAt(av.getCreatedAt())
                        .updatedBy(av.getUpdatedBy())
                        .updatedAt(av.getUpdatedAt())
                        .build())
                .collect(Collectors.toSet());

        Set<Long> attributeValueIds = entity.getProductAttributeValues().stream()
                .map(ProductAttributeValue::getId)
                .collect(Collectors.toSet());

        Set<Long> discountIds = entity.getDiscounts().stream()
                .map(Discount::getId)
                .collect(Collectors.toSet());

        return ResProductVariantDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .totalQuantity(entity.getTotalQuantity())
                .minPrice(entity.getMinPrice())
                .sku(entity.getSku())
                .active(entity.getActive())
                .productAttributeValues(attributeValuesDTOs)
                .productAttributeValueIds(attributeValueIds)
                .productId(entity.getProduct() != null ? entity.getProduct().getId() : null)
                .orderItemId(entity.getOrderItem() != null ? entity.getOrderItem().getId() : null)
                .discountIds(discountIds)
                .inventoryId(entity.getInventory() != null ? entity.getInventory().getId() : null)
                .createdBy(entity.getCreatedBy())
                .createdAt(entity.getCreatedAt())
                .updatedBy(entity.getUpdatedBy())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
