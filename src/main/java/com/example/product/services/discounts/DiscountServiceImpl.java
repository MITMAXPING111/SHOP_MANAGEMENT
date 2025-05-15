package com.example.product.services.discounts;

import com.example.product.entities.Discount;
import com.example.product.models.request.ReqDiscountDTO;
import com.example.product.models.response.ResDiscountDTO;
import com.example.product.repositories.DiscountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiscountServiceImpl implements DiscountService {

    @Autowired
    private DiscountRepository discountRepository;

    @Override
    public ResDiscountDTO createDiscount(ReqDiscountDTO dto) {
        Discount discount = new Discount();
        discount.setCode(dto.getCode());
        discount.setDescription(dto.getDescription());
        discount.setPercentage(dto.getPercentage());
        discount.setMaxAmount(dto.getMaxAmount());
        discount.setStartDate(dto.getStartDate());
        discount.setEndDate(dto.getEndDate());
        discount.setActive(dto.getActive());
        discount.setCreatedAt(dto.getCreatedAt() != null ? dto.getCreatedAt() : LocalDateTime.now());
        discount.setUpdatedAt(dto.getUpdatedAt() != null ? dto.getUpdatedAt() : LocalDateTime.now());
        discount.setCreatedBy(dto.getCreatedBy());
        discount.setUpdatedBy(dto.getUpdatedBy());

        return mapToResDTO(discountRepository.save(discount));
    }

    @Override
    public ResDiscountDTO updateDiscount(Long id, ReqDiscountDTO dto) {
        Discount discount = discountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Discount not found with ID: " + id));

        discount.setCode(dto.getCode());
        discount.setDescription(dto.getDescription());
        discount.setPercentage(dto.getPercentage());
        discount.setMaxAmount(dto.getMaxAmount());
        discount.setStartDate(dto.getStartDate());
        discount.setEndDate(dto.getEndDate());
        discount.setActive(dto.getActive());
        discount.setUpdatedAt(LocalDateTime.now());
        discount.setUpdatedBy(dto.getUpdatedBy());

        return mapToResDTO(discountRepository.save(discount));
    }

    @Override
    public void deleteDiscount(Long id) {
        discountRepository.deleteById(id);
    }

    @Override
    public ResDiscountDTO getDiscountById(Long id) {
        return discountRepository.findById(id)
                .map(this::mapToResDTO)
                .orElseThrow(() -> new RuntimeException("Discount not found with ID: " + id));
    }

    @Override
    public List<ResDiscountDTO> getAllDiscounts() {
        return discountRepository.findAll()
                .stream()
                .map(this::mapToResDTO)
                .collect(Collectors.toList());
    }

    private ResDiscountDTO mapToResDTO(Discount discount) {
        return new ResDiscountDTO(
                discount.getId(),
                discount.getCode(),
                discount.getDescription(),
                discount.getPercentage(),
                discount.getMaxAmount(),
                discount.getStartDate(),
                discount.getEndDate(),
                discount.getActive(),
                discount.getCreatedAt(),
                discount.getUpdatedAt(),
                discount.getCreatedBy(),
                discount.getUpdatedBy());
    }
}
