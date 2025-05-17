package com.example.product.services.products;

import java.util.List;

import com.example.product.models.request.products.ReqProductDTO;
import com.example.product.models.response.products.ResProductDTO;

public interface ProductService {

    ResProductDTO createProduct(ReqProductDTO reqProductDTO);

    ResProductDTO updateProduct(Long id, ReqProductDTO reqProductDTO);

    void deleteProduct(Long id);

    ResProductDTO getProductById(Long id);

    List<ResProductDTO> getAllProducts();
}
