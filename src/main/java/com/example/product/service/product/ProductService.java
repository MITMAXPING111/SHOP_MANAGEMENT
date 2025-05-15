package com.example.product.service.product;

import java.util.List;

import com.example.product.model.request.ReqProductDTO;
import com.example.product.model.response.ResProductDTO;

public interface ProductService {
    ResProductDTO createProduct(ReqProductDTO reqProductDTO);

    ResProductDTO updateProduct(Long id, ReqProductDTO reqProductDTO);

    void deleteProduct(Long id);

    ResProductDTO getProductById(Long id);

    List<ResProductDTO> getAllProducts();
}
