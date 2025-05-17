package com.example.product.services.addresses;

import com.example.product.entities.users.Address;
import com.example.product.models.request.users.ReqAddressDTO;
import com.example.product.models.response.users.ResAddressDTO;
import com.example.product.repositories.AddressRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    @Override
    public ResAddressDTO createAddress(ReqAddressDTO dto) {
        Address address = new Address();
        address.setProvince(dto.getProvince());
        address.setWard(dto.getWard());
        address.setAddressDetail(dto.getAddressDetail());
        address.setType(dto.getType());

        address.setCreatedBy(dto.getCreatedBy());
        address.setCreatedAt(LocalDateTime.now());

        Address saved = addressRepository.save(address);
        return mapToDTO(saved);
    }

    @Override
    public ResAddressDTO updateAddress(Long id, ReqAddressDTO dto) {
        Optional<Address> optional = addressRepository.findById(id);
        if (optional.isEmpty()) {
            throw new RuntimeException("Address not found with id: " + id);
        }

        Address address = optional.get();
        address.setProvince(dto.getProvince());
        address.setWard(dto.getWard());
        address.setAddressDetail(dto.getAddressDetail());
        address.setType(dto.getType());

        // Cập nhật metadata
        address.setUpdatedBy(dto.getUpdatedBy());
        address.setUpdatedAt(LocalDateTime.now());

        Address updated = addressRepository.save(address);
        return mapToDTO(updated);
    }

    @Override
    public void deleteAddress(Long id) {
        if (!addressRepository.existsById(id)) {
            throw new RuntimeException("Address not found with id: " + id);
        }
        addressRepository.deleteById(id);
    }

    @Override
    public ResAddressDTO getAddressById(Long id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Address not found with id: " + id));
        return mapToDTO(address);
    }

    @Override
    public List<ResAddressDTO> getAllAddresses() {
        return addressRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private ResAddressDTO mapToDTO(Address address) {
        return new ResAddressDTO(
                address.getId(),
                address.getProvince(),
                address.getWard(),
                address.getAddressDetail(),
                address.getType(),
                address.getCreatedBy(),
                address.getCreatedAt(),
                address.getUpdatedBy(),
                address.getUpdatedAt());
    }
}
