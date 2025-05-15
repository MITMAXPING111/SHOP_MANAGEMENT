package com.example.product.services.suppliers;

import java.util.List;

import com.example.product.models.request.ReqSupplierDTO;
import com.example.product.models.response.ResSupplierDTO;

public interface SupplierService {
    ResSupplierDTO createSupplier(ReqSupplierDTO dto);

    ResSupplierDTO updateSupplier(Long id, ReqSupplierDTO dto);

    void deleteSupplier(Long id);

    ResSupplierDTO getSupplierById(Long id);

    List<ResSupplierDTO> getAllSuppliers();
}