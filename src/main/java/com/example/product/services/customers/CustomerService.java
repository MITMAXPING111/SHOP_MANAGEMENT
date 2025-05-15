package com.example.product.services.customers;

import java.util.List;

import com.example.product.models.request.ReqCustomerDTO;
import com.example.product.models.response.ResCustomerDTO;

public interface CustomerService {
    ResCustomerDTO createCustomer(ReqCustomerDTO dto);

    ResCustomerDTO updateCustomer(Long id, ReqCustomerDTO dto);

    void deleteCustomer(Long id);

    ResCustomerDTO getCustomerById(Long id);

    List<ResCustomerDTO> getAllCustomers();
}
