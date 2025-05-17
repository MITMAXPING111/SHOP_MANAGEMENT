package com.example.product.services.productAttributeValue;

import com.example.product.models.request.products.ReqProductAttributeValueDTO;
import com.example.product.models.response.products.ResProductAttributeValueDTO;

import java.util.List;

public interface ProductAttributeValueService {

    ResProductAttributeValueDTO create(ReqProductAttributeValueDTO request);

    ResProductAttributeValueDTO update(Long id, ReqProductAttributeValueDTO request);

    void delete(Long id);

    ResProductAttributeValueDTO getById(Long id);

    List<ResProductAttributeValueDTO> getAll();
}
