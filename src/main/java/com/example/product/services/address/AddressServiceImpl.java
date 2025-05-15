package com.example.product.services.address;

import com.example.product.entities.Address;
import com.example.product.entities.Customer;
import com.example.product.models.request.ReqAddressDTO;
import com.example.product.models.response.ResAddressDTO;
import com.example.product.repositories.AddressRepository;
import com.example.product.repositories.CustomerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public ResAddressDTO createAddress(ReqAddressDTO dto) {
        Customer customer = customerRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + dto.getCustomerId()));

        Address address = new Address();
        address.setStreet(dto.getStreet());
        address.setCity(dto.getCity());
        address.setState(dto.getState());
        address.setZipCode(dto.getZipCode());
        address.setCountry(dto.getCountry());
        address.setCustomer(customer);
        address.setType(dto.getType());
        address.setCreatedAt(dto.getCreatedAt() != null ? dto.getCreatedAt() : LocalDateTime.now());
        address.setUpdatedAt(dto.getUpdatedAt() != null ? dto.getUpdatedAt() : LocalDateTime.now());
        address.setCreatedBy(dto.getCreatedBy());
        address.setUpdatedBy(dto.getUpdatedBy());

        return mapToResDTO(addressRepository.save(address));
    }

    @Override
    public ResAddressDTO updateAddress(Long id, ReqAddressDTO dto) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Address not found with ID: " + id));

        Customer customer = customerRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + dto.getCustomerId()));

        address.setStreet(dto.getStreet());
        address.setCity(dto.getCity());
        address.setState(dto.getState());
        address.setZipCode(dto.getZipCode());
        address.setCountry(dto.getCountry());
        address.setCustomer(customer);
        address.setType(dto.getType());
        address.setUpdatedAt(LocalDateTime.now());
        address.setUpdatedBy(dto.getUpdatedBy());

        return mapToResDTO(addressRepository.save(address));
    }

    @Override
    public void deleteAddress(Long id) {
        addressRepository.deleteById(id);
    }

    @Override
    public ResAddressDTO getAddressById(Long id) {
        return addressRepository.findById(id)
                .map(this::mapToResDTO)
                .orElseThrow(() -> new RuntimeException("Address not found with ID: " + id));
    }

    @Override
    public List<ResAddressDTO> getAllAddresses() {
        return addressRepository.findAll()
                .stream()
                .map(this::mapToResDTO)
                .collect(Collectors.toList());
    }

    private ResAddressDTO mapToResDTO(Address address) {
        return new ResAddressDTO(
                address.getId(),
                address.getStreet(),
                address.getCity(),
                address.getState(),
                address.getZipCode(),
                address.getCountry(),
                address.getCustomer() != null ? address.getCustomer().getId() : null,
                address.getType(),
                address.getCreatedAt(),
                address.getUpdatedAt(),
                address.getCreatedBy(),
                address.getUpdatedBy());
    }
}