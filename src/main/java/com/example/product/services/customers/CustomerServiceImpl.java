package com.example.product.services.customers;

import com.example.product.entities.Customer;
import com.example.product.models.request.ReqCustomerDTO;
import com.example.product.models.response.ResCustomerDTO;
import com.example.product.repositories.CustomerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public ResCustomerDTO createCustomer(ReqCustomerDTO dto) {
        Customer customer = mapToEntity(dto);
        customer.setCreatedAt(LocalDateTime.now());
        customer.setCreatedBy(dto.getCreatedBy());
        Customer saved = customerRepository.save(customer);
        return mapToResDTO(saved);
    }

    @Override
    public ResCustomerDTO updateCustomer(Long id, ReqCustomerDTO dto) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + id));
        customer.setFullName(dto.getFullName());
        customer.setEmail(dto.getEmail());
        customer.setPhone(dto.getPhone());
        customer.setUpdatedAt(LocalDateTime.now());
        customer.setUpdatedBy(dto.getUpdatedBy());

        return mapToResDTO(customerRepository.save(customer));
    }

    @Override
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

    @Override
    public ResCustomerDTO getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + id));
        return mapToResDTO(customer);
    }

    @Override
    public List<ResCustomerDTO> getAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(this::mapToResDTO)
                .collect(Collectors.toList());
    }

    private Customer mapToEntity(ReqCustomerDTO dto) {
        Customer customer = new Customer();
        customer.setFullName(dto.getFullName());
        customer.setEmail(dto.getEmail());
        customer.setPhone(dto.getPhone());
        customer.setCreatedBy(dto.getCreatedBy());
        customer.setUpdatedBy(dto.getUpdatedBy());
        return customer;
    }

    private ResCustomerDTO mapToResDTO(Customer customer) {
        ResCustomerDTO dto = new ResCustomerDTO();
        dto.setId(customer.getId());
        customer.setFullName(dto.getFullName());
        dto.setEmail(customer.getEmail());
        dto.setPhone(customer.getPhone());
        dto.setCreatedAt(customer.getCreatedAt());
        dto.setUpdatedAt(customer.getUpdatedAt());
        dto.setCreatedBy(customer.getCreatedBy());
        dto.setUpdatedBy(customer.getUpdatedBy());
        return dto;
    }
}