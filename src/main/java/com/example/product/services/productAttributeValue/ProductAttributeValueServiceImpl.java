package com.example.product.services.productAttributeValue;

import com.example.product.entities.products.ProductAttribute;
import com.example.product.entities.products.ProductAttributeValue;
import com.example.product.models.request.products.ReqProductAttributeValueDTO;
import com.example.product.models.response.products.ResProductAttributeValueDTO;
import com.example.product.repositories.ProductAttributeRepository;
import com.example.product.repositories.ProductAttributeValueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductAttributeValueServiceImpl implements ProductAttributeValueService {

    private final ProductAttributeValueRepository attributeValueRepository;
    private final ProductAttributeRepository productAttributeRepository;

    @Override
    public ResProductAttributeValueDTO create(ReqProductAttributeValueDTO request) {
        ProductAttribute attribute = productAttributeRepository.findById(request.getProductAttributeId())
                .orElseThrow(() -> new RuntimeException(
                        "ProductAttribute not found with ID: " + request.getProductAttributeId()));

        ProductAttributeValue value = ProductAttributeValue.builder()
                .name(request.getName())
                .productAttribute(attribute)
                .createdBy(request.getCreatedBy())
                .createdAt(LocalDateTime.now())
                .build();

        attributeValueRepository.save(value);
        return toDTO(value);
    }

    @Override
    public ResProductAttributeValueDTO update(Long id, ReqProductAttributeValueDTO request) {
        ProductAttributeValue value = attributeValueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ProductAttributeValue not found with ID: " + id));

        value.setName(request.getName());
        value.setUpdatedBy(request.getUpdatedBy());
        value.setUpdatedAt(LocalDateTime.now());

        attributeValueRepository.save(value);
        return toDTO(value);
    }

    @Override
    public void delete(Long id) {
        ProductAttributeValue value = attributeValueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ProductAttributeValue not found with ID: " + id));
        attributeValueRepository.delete(value);
    }

    @Override
    public ResProductAttributeValueDTO getById(Long id) {
        ProductAttributeValue value = attributeValueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ProductAttributeValue not found with ID: " + id));
        return toDTO(value);
    }

    @Override
    public List<ResProductAttributeValueDTO> getAll() {
        return attributeValueRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private ResProductAttributeValueDTO toDTO(ProductAttributeValue value) {
        return ResProductAttributeValueDTO.builder()
                .id(value.getId())
                .name(value.getName())
                .productAttributeId(
                        value.getProductAttribute() != null ? value.getProductAttribute().getId() : null)
                .createdBy(value.getCreatedBy())
                .createdAt(value.getCreatedAt())
                .updatedBy(value.getUpdatedBy())
                .updatedAt(value.getUpdatedAt())
                .build();
    }
}
