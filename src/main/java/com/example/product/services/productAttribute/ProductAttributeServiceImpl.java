package com.example.product.services.productAttribute;

import com.example.product.entities.products.ProductAttribute;
import com.example.product.entities.products.ProductAttributeValue;
import com.example.product.models.request.products.ReqProductAttributeDTO;
import com.example.product.models.response.products.ResProductAttributeDTO;
import com.example.product.models.response.products.ResProductAttributeValueDTO;
import com.example.product.repositories.ProductAttributeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductAttributeServiceImpl implements ProductAttributeService {

    private final ProductAttributeRepository productAttributeRepository;

    @Override
    public ResProductAttributeDTO createProductAttribute(ReqProductAttributeDTO req) {
        ProductAttribute entity = mapToEntity(req);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setCreatedBy(req.getCreatedBy());
        productAttributeRepository.save(entity);
        return mapToDTO(entity);
    }

    @Override
    public ResProductAttributeDTO updateProductAttribute(Long id, ReqProductAttributeDTO req) {
        ProductAttribute entity = productAttributeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ProductAttribute not found with id: " + id));

        entity.setName(req.getName());
        entity.setUpdatedAt(LocalDateTime.now());
        entity.setUpdatedBy(req.getUpdatedBy());

        productAttributeRepository.save(entity);
        return mapToDTO(entity);
    }

    @Override
    public void deleteProductAttribute(Long id) {
        ProductAttribute entity = productAttributeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ProductAttribute not found with id: " + id));
        productAttributeRepository.delete(entity);
    }

    @Override
    public ResProductAttributeDTO getProductAttributeById(Long id) {
        ProductAttribute entity = productAttributeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ProductAttribute not found with id: " + id));
        return mapToDTO(entity);
    }

    @Override
    public List<ResProductAttributeDTO> getAllProductAttributes() {
        return productAttributeRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private ProductAttribute mapToEntity(ReqProductAttributeDTO req) {
        ProductAttribute entity = new ProductAttribute();
        entity.setName(req.getName());
        entity.setCreatedBy(req.getCreatedBy());
        entity.setCreatedAt(req.getCreatedAt());
        entity.setUpdatedBy(req.getUpdatedBy());
        entity.setUpdatedAt(req.getUpdatedAt());

        if (req.getProductAttributeValues() != null && !req.getProductAttributeValues().isEmpty()) {
            List<ProductAttributeValue> valueEntities = req.getProductAttributeValues().stream()
                    .map(dto -> {
                        ProductAttributeValue val = new ProductAttributeValue();
                        val.setName(dto.getName());
                        val.setCreatedBy(dto.getCreatedBy());
                        val.setCreatedAt(dto.getCreatedAt());
                        val.setProductAttribute(entity); // liên kết ngược
                        return val;
                    })
                    .collect(Collectors.toList());
            entity.setProductAttributeValues(valueEntities);
        }

        return entity;
    }

    private ResProductAttributeDTO mapToDTO(ProductAttribute entity) {
        List<ResProductAttributeValueDTO> valueDTOs = null;
        if (entity.getProductAttributeValues() != null) {
            valueDTOs = entity.getProductAttributeValues().stream()
                    .map((ProductAttributeValue value) -> ResProductAttributeValueDTO.builder()
                            .id(value.getId())
                            .name(value.getName())
                            .productAttributeId(
                                    value.getProductAttribute() != null ? value.getProductAttribute().getId() : null)
                            .createdAt(value.getCreatedAt())
                            .updatedAt(value.getUpdatedAt())
                            .createdBy(value.getCreatedBy())
                            .updatedBy(value.getUpdatedBy())
                            .build())
                    .collect(Collectors.toList());
        }

        return ResProductAttributeDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .productAttributeValues(valueDTOs)
                .createdAt(entity.getCreatedAt())
                .createdBy(entity.getCreatedBy())
                .updatedAt(entity.getUpdatedAt())
                .updatedBy(entity.getUpdatedBy())
                .build();
    }
}
