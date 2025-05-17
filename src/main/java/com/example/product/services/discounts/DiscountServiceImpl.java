package com.example.product.services.discounts;

import com.example.product.entities.managers.Discount;
import com.example.product.entities.products.ProductVariant;
import com.example.product.models.request.managers.ReqDiscountDTO;
import com.example.product.models.response.managers.ResDiscountDTO;
import com.example.product.models.response.products.ResProductVariantDTO;
import com.example.product.models.response.products.ResProductAttributeValueDTO;
import com.example.product.repositories.DiscountRepository;
import com.example.product.repositories.ProductVariantRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class DiscountServiceImpl implements DiscountService {

    private final DiscountRepository discountRepository;
    private final ProductVariantRepository productVariantRepository;

    @Override
    public ResDiscountDTO createDiscount(ReqDiscountDTO dto) {
        Discount entity = mapToEntity(dto);
        entity.setCreatedAt(LocalDateTime.now());

        // Lấy productVariants từ ID
        if (dto.getProductVariantIds() != null && !dto.getProductVariantIds().isEmpty()) {
            Set<ProductVariant> variants = new HashSet<>(
                    productVariantRepository.findAllById(dto.getProductVariantIds()));
            entity.setProductVariants(variants);
        }

        Discount saved = discountRepository.save(entity);
        return mapToDTO(saved);
    }

    @Override
    public ResDiscountDTO updateDiscount(Long id, ReqDiscountDTO dto) {
        Discount entity = discountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Discount not found with id " + id));

        // Cập nhật các trường
        entity.setCode(dto.getCode());
        entity.setDescription(dto.getDescription());
        entity.setPercentage(dto.getPercentage());
        entity.setMaxAmount(dto.getMaxAmount());
        entity.setStartDate(dto.getStartDate());
        entity.setEndDate(dto.getEndDate());
        entity.setActive(dto.getActive());
        entity.setUpdatedBy(dto.getUpdatedBy());
        entity.setUpdatedAt(LocalDateTime.now());

        // Cập nhật productVariants
        if (dto.getProductVariantIds() != null) {
            Set<ProductVariant> variants = new HashSet<>(
                    productVariantRepository.findAllById(dto.getProductVariantIds()));
            entity.setProductVariants(variants);
        }

        Discount updated = discountRepository.save(entity);
        return mapToDTO(updated);
    }

    @Override
    public void deleteDiscount(Long id) {
        discountRepository.deleteById(id);
    }

    @Override
    public ResDiscountDTO getDiscountById(Long id) {
        Discount entity = discountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Discount not found with id " + id));
        return mapToDTO(entity);
    }

    @Override
    public java.util.List<ResDiscountDTO> getAllDiscounts() {
        return discountRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // --- Helper methods ---

    private Discount mapToEntity(ReqDiscountDTO dto) {
        Discount entity = new Discount();
        entity.setCode(dto.getCode());
        entity.setDescription(dto.getDescription());
        entity.setPercentage(dto.getPercentage());
        entity.setMaxAmount(dto.getMaxAmount());
        entity.setStartDate(dto.getStartDate());
        entity.setEndDate(dto.getEndDate());
        entity.setActive(dto.getActive());

        entity.setCreatedBy(dto.getCreatedBy());
        entity.setCreatedAt(dto.getCreatedAt() != null ? dto.getCreatedAt() : LocalDateTime.now());
        entity.setUpdatedBy(dto.getUpdatedBy());
        entity.setUpdatedAt(dto.getUpdatedAt() != null ? dto.getUpdatedAt() : LocalDateTime.now());

        return entity;
    }

    private ResDiscountDTO mapToDTO(Discount entity) {
        ResDiscountDTO dto = new ResDiscountDTO();
        dto.setId(entity.getId());
        dto.setCode(entity.getCode());
        dto.setDescription(entity.getDescription());
        dto.setPercentage(entity.getPercentage());
        dto.setMaxAmount(entity.getMaxAmount());
        dto.setStartDate(entity.getStartDate());
        dto.setEndDate(entity.getEndDate());
        dto.setActive(entity.getActive());

        dto.setCreatedBy(entity.getCreatedBy());
        dto.setCreatedAt(entity.getCreatedAt() != null ? entity.getCreatedAt() : LocalDateTime.now());
        dto.setUpdatedBy(entity.getUpdatedBy());
        dto.setUpdatedAt(entity.getUpdatedAt() != null ? entity.getUpdatedAt() : LocalDateTime.now());

        // Ánh xạ productVariants
        Set<ResProductVariantDTO> variantDTOs = entity.getProductVariants()
                .stream()
                .map(variant -> {
                    ResProductVariantDTO vdto = new ResProductVariantDTO();
                    vdto.setId(variant.getId());
                    vdto.setName(variant.getName());
                    vdto.setDescription(variant.getDescription());
                    vdto.setTotalQuantity(variant.getTotalQuantity());
                    vdto.setMinPrice(variant.getMinPrice());
                    vdto.setSku(variant.getSku());
                    vdto.setActive(variant.getActive());

                    // Kiểm tra null trước khi set
                    vdto.setProductId(variant.getProduct() != null ? variant.getProduct().getId() : null);
                    vdto.setInventoryId(variant.getInventory() != null ? variant.getInventory().getId() : null);

                    vdto.setCreatedBy(variant.getCreatedBy());
                    vdto.setCreatedAt(variant.getCreatedAt() != null ? variant.getCreatedAt() : LocalDateTime.now());
                    vdto.setUpdatedBy(variant.getUpdatedBy());
                    vdto.setUpdatedAt(variant.getUpdatedAt() != null ? variant.getUpdatedAt() : LocalDateTime.now());

                    // Ánh xạ productAttributeValues nếu có
                    if (variant.getProductAttributeValues() != null) {
                        Set<ResProductAttributeValueDTO> attrDTOs = variant.getProductAttributeValues()
                                .stream()
                                .map(attr -> {
                                    ResProductAttributeValueDTO attrDTO = new ResProductAttributeValueDTO();
                                    attrDTO.setId(attr.getId());
                                    attrDTO.setName(attr.getName());
                                    attrDTO.setProductAttributeId(attr.getId());
                                    return attrDTO;
                                })
                                .collect(Collectors.toSet());
                        vdto.setProductAttributeValues(attrDTOs);
                    }

                    return vdto;
                })
                .collect(Collectors.toSet());

        dto.setProductVariantDTOs(variantDTOs);

        return dto;
    }
}
