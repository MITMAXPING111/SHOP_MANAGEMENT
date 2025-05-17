package com.example.product.controllers;

import com.example.product.models.request.users.ReqAddressDTO;
import com.example.product.models.response.users.ResAddressDTO;
import com.example.product.services.addresses.AddressService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @PostMapping
    public ResponseEntity<ResAddressDTO> createAddress(@RequestBody ReqAddressDTO dto) {
        ResAddressDTO result = addressService.createAddress(dto);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResAddressDTO> updateAddress(@PathVariable Long id, @RequestBody ReqAddressDTO dto) {
        ResAddressDTO result = addressService.updateAddress(id, dto);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long id) {
        addressService.deleteAddress(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResAddressDTO> getAddressById(@PathVariable Long id) {
        ResAddressDTO result = addressService.getAddressById(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping
    public ResponseEntity<List<ResAddressDTO>> getAllAddresses() {
        List<ResAddressDTO> results = addressService.getAllAddresses();
        return ResponseEntity.ok(results);
    }
}
