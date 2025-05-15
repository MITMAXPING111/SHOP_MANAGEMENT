package com.example.product.services.suppliers;

import com.example.product.entities.Supplier;
import com.example.product.models.request.ReqSupplierDTO;
import com.example.product.models.response.ResSupplierDTO;
import com.example.product.repositories.SupplierRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupplierServiceImpl implements SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    @Override
    public ResSupplierDTO createSupplier(ReqSupplierDTO dto) {
        Supplier supplier = new Supplier();
        supplier.setName(dto.getName());
        supplier.setContactName(dto.getContactName());
        supplier.setPhone(dto.getPhone());
        supplier.setEmail(dto.getEmail());
        supplier.setAddress(dto.getAddress());
        supplier.setCreatedAt(dto.getCreatedAt() != null ? dto.getCreatedAt() : LocalDateTime.now());
        supplier.setUpdatedAt(dto.getUpdatedAt() != null ? dto.getUpdatedAt() : LocalDateTime.now());
        supplier.setCreatedBy(dto.getCreatedBy());
        supplier.setUpdatedBy(dto.getUpdatedBy());

        return mapToResDTO(supplierRepository.save(supplier));
    }

    @Override
    public ResSupplierDTO updateSupplier(Long id, ReqSupplierDTO dto) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Supplier not found with ID: " + id));

        supplier.setName(dto.getName());
        supplier.setContactName(dto.getContactName());
        supplier.setPhone(dto.getPhone());
        supplier.setEmail(dto.getEmail());
        supplier.setAddress(dto.getAddress());
        supplier.setUpdatedAt(LocalDateTime.now());
        supplier.setUpdatedBy(dto.getUpdatedBy());

        return mapToResDTO(supplierRepository.save(supplier));
    }

    @Override
    public void deleteSupplier(Long id) {
        supplierRepository.deleteById(id);
    }

    @Override
    public ResSupplierDTO getSupplierById(Long id) {
        return supplierRepository.findById(id)
                .map(this::mapToResDTO)
                .orElseThrow(() -> new RuntimeException("Supplier not found with ID: " + id));
    }

    @Override
    public List<ResSupplierDTO> getAllSuppliers() {
        return supplierRepository.findAll()
                .stream()
                .map(this::mapToResDTO)
                .collect(Collectors.toList());
    }

    private ResSupplierDTO mapToResDTO(Supplier supplier) {
        return new ResSupplierDTO(
                supplier.getId(),
                supplier.getName(),
                supplier.getContactName(),
                supplier.getPhone(),
                supplier.getEmail(),
                supplier.getAddress(),
                supplier.getCreatedAt(),
                supplier.getUpdatedAt(),
                supplier.getCreatedBy(),
                supplier.getUpdatedBy());
    }
}
