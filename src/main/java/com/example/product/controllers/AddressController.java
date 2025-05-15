package com.example.product.controllers;

import com.example.product.models.request.ReqAddressDTO;
import com.example.product.models.response.ResAddressDTO;
import com.example.product.services.address.AddressService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @PostMapping
    public ResponseEntity<ResAddressDTO> createAddress(@RequestBody ReqAddressDTO dto) {
        ResAddressDTO createdAddress = addressService.createAddress(dto);
        return ResponseEntity.ok(createdAddress);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResAddressDTO> updateAddress(@PathVariable Long id,
            @RequestBody ReqAddressDTO dto) {
        ResAddressDTO updatedAddress = addressService.updateAddress(id, dto);
        return ResponseEntity.ok(updatedAddress);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long id) {
        addressService.deleteAddress(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResAddressDTO> getAddressById(@PathVariable Long id) {
        ResAddressDTO address = addressService.getAddressById(id);
        return ResponseEntity.ok(address);
    }

    @GetMapping
    public ResponseEntity<List<ResAddressDTO>> getAllAddresses() {
        List<ResAddressDTO> addresses = addressService.getAllAddresses();
        return ResponseEntity.ok(addresses);
    }
}
