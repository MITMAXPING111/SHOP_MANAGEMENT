package com.example.product.controllers;

import com.example.product.models.request.ReqCustomerDTO;
import com.example.product.models.response.ResCustomerDTO;
import com.example.product.services.customers.CustomerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping
    public ResponseEntity<ResCustomerDTO> createCustomer(@RequestBody ReqCustomerDTO dto) {
        ResCustomerDTO createdCustomer = customerService.createCustomer(dto);
        return ResponseEntity.ok(createdCustomer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResCustomerDTO> updateCustomer(@PathVariable Long id, @RequestBody ReqCustomerDTO dto) {
        ResCustomerDTO updatedCustomer = customerService.updateCustomer(id, dto);
        return ResponseEntity.ok(updatedCustomer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResCustomerDTO> getCustomerById(@PathVariable Long id) {
        ResCustomerDTO customer = customerService.getCustomerById(id);
        return ResponseEntity.ok(customer);
    }

    @GetMapping
    public ResponseEntity<List<ResCustomerDTO>> getAllCustomers() {
        List<ResCustomerDTO> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }
}
