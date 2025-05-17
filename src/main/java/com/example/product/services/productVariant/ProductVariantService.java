package com.example.product.services.productVariant;

import com.example.product.models.request.products.ReqProductVariantDTO;
import com.example.product.models.response.products.ResProductVariantDTO;

import java.util.List;

public interface ProductVariantService {
    ResProductVariantDTO create(ReqProductVariantDTO request);

    ResProductVariantDTO update(Long id, ReqProductVariantDTO request);

    void delete(Long id);

    ResProductVariantDTO getById(Long id);

    List<ResProductVariantDTO> getAll();
}
