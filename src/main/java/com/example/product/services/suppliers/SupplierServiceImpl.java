package com.example.product.services.suppliers;

import com.example.product.entities.managers.Supplier;
import com.example.product.models.request.managers.ReqSupplierDTO;
import com.example.product.models.response.managers.ResSupplierDTO;
import com.example.product.repositories.SupplierRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;

    @Override
    public ResSupplierDTO createSupplier(ReqSupplierDTO dto) {
        Supplier supplier = mapToEntity(dto);
        supplier = supplierRepository.save(supplier);
        return mapToDTO(supplier);
    }

    @Override
    public ResSupplierDTO updateSupplier(Long id, ReqSupplierDTO dto) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Supplier not found with ID: " + id));

        supplier.setName(dto.getName());
        supplier.setContactName(dto.getContactName());
        supplier.setPhone(dto.getPhone());
        supplier.setEmail(dto.getEmail());
        supplier.setProvince(dto.getProvince());
        supplier.setWard(dto.getWard());
        supplier.setAddressDetail(dto.getAddressDetail());
        supplier.setUpdatedAt(dto.getUpdatedAt());
        supplier.setUpdatedBy(dto.getUpdatedBy());

        return mapToDTO(supplierRepository.save(supplier));
    }

    @Override
    public void deleteSupplier(Long id) {
        if (!supplierRepository.existsById(id)) {
            throw new EntityNotFoundException("Supplier not found with ID: " + id);
        }
        supplierRepository.deleteById(id);
    }

    @Override
    public ResSupplierDTO getSupplierById(Long id) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Supplier not found with ID: " + id));
        return mapToDTO(supplier);
    }

    @Override
    public List<ResSupplierDTO> getAllSuppliers() {
        return supplierRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private Supplier mapToEntity(ReqSupplierDTO dto) {
        Supplier supplier = new Supplier();
        supplier.setName(dto.getName());
        supplier.setContactName(dto.getContactName());
        supplier.setPhone(dto.getPhone());
        supplier.setEmail(dto.getEmail());
        supplier.setProvince(dto.getProvince());
        supplier.setWard(dto.getWard());
        supplier.setAddressDetail(dto.getAddressDetail());
        supplier.setCreatedAt(dto.getCreatedAt());
        supplier.setUpdatedAt(dto.getUpdatedAt());
        supplier.setCreatedBy(dto.getCreatedBy());
        supplier.setUpdatedBy(dto.getUpdatedBy());
        return supplier;
    }

    private ResSupplierDTO mapToDTO(Supplier supplier) {
        return new ResSupplierDTO(
                supplier.getId(),
                supplier.getName(),
                supplier.getContactName(),
                supplier.getPhone(),
                supplier.getEmail(),
                supplier.getProvince(),
                supplier.getWard(),
                supplier.getAddressDetail(),
                supplier.getCreatedAt(),
                supplier.getUpdatedAt(),
                supplier.getCreatedBy(),
                supplier.getUpdatedBy(),
                supplier.getProducts().stream()
                        .map(product -> product.getId())
                        .collect(Collectors.toList()));
    }
}
