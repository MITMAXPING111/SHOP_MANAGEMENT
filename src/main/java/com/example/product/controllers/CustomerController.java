package com.example.product.controllers;

import com.example.product.models.request.users.ReqCustomerDTO;
import com.example.product.models.response.users.ResCustomerDTO;
import com.example.product.services.customers.CustomerService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping
    public ResponseEntity<ResCustomerDTO> createCustomer(@Valid @RequestBody ReqCustomerDTO reqCustomerDTO) {
        String hashPassword = this.passwordEncoder.encode(reqCustomerDTO.getPassword());
        reqCustomerDTO.setPassword(hashPassword);
        ResCustomerDTO created = customerService.createCustomer(reqCustomerDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResCustomerDTO> updateCustomer(
            @PathVariable Long id,
            @Valid @RequestBody ReqCustomerDTO reqCustomerDTO) {
        ResCustomerDTO updated = customerService.updateCustomer(id, reqCustomerDTO);
        return ResponseEntity.ok(updated);
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}
