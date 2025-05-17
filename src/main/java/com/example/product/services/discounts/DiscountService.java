package com.example.product.services.discounts;

import com.example.product.models.request.managers.ReqDiscountDTO;
import com.example.product.models.response.managers.ResDiscountDTO;

import java.util.List;

public interface DiscountService {

    ResDiscountDTO createDiscount(ReqDiscountDTO dto);

    ResDiscountDTO updateDiscount(Long id, ReqDiscountDTO dto);

    void deleteDiscount(Long id);

    ResDiscountDTO getDiscountById(Long id);

    List<ResDiscountDTO> getAllDiscounts();
}
