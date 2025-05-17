package com.example.product.services.productAttribute;

import com.example.product.models.request.products.ReqProductAttributeDTO;
import com.example.product.models.response.products.ResProductAttributeDTO;

import java.util.List;

public interface ProductAttributeService {
    ResProductAttributeDTO createProductAttribute(ReqProductAttributeDTO req);

    ResProductAttributeDTO updateProductAttribute(Long id, ReqProductAttributeDTO req);

    void deleteProductAttribute(Long id);

    ResProductAttributeDTO getProductAttributeById(Long id);

    List<ResProductAttributeDTO> getAllProductAttributes();
}
