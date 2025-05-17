package com.example.product.services.suppliers;

import com.example.product.models.request.managers.ReqSupplierDTO;
import com.example.product.models.response.managers.ResSupplierDTO;

import java.util.List;

public interface SupplierService {
    ResSupplierDTO createSupplier(ReqSupplierDTO dto);

    ResSupplierDTO updateSupplier(Long id, ReqSupplierDTO dto);

    void deleteSupplier(Long id);

    ResSupplierDTO getSupplierById(Long id);

    List<ResSupplierDTO> getAllSuppliers();
}
