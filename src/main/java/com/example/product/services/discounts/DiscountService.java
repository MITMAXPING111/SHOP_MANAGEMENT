package com.example.product.services.discounts;

import java.util.List;

import com.example.product.models.request.ReqDiscountDTO;
import com.example.product.models.response.ResDiscountDTO;

public interface DiscountService {
    ResDiscountDTO createDiscount(ReqDiscountDTO dto);

    ResDiscountDTO updateDiscount(Long id, ReqDiscountDTO dto);

    void deleteDiscount(Long id);

    ResDiscountDTO getDiscountById(Long id);

    List<ResDiscountDTO> getAllDiscounts();
}
