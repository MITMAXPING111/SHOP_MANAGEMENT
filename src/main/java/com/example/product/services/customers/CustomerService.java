package com.example.product.services.customers;

import java.util.List;

import com.example.product.entities.users.Customer;
import com.example.product.entities.users.User;
import com.example.product.models.request.users.ReqCustomerDTO;
import com.example.product.models.response.users.ResCustomerDTO;

public interface CustomerService {

    ResCustomerDTO createCustomer(ReqCustomerDTO reqCustomerDTO);

    ResCustomerDTO updateCustomer(Long id, ReqCustomerDTO reqCustomerDTO);

    ResCustomerDTO getCustomerById(Long id);

    List<ResCustomerDTO> getAllCustomers();

    void deleteCustomer(Long id);

    Customer handleGetCustomerByUsername(String username);
}
