package com.example.product.controllers;

import com.example.product.models.request.users.ReqAccountImageDTO;
import com.example.product.models.response.users.ResAccountImageDTO;
import com.example.product.services.AccountImage.AccountImageService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/account-images")
@RequiredArgsConstructor
public class AccountImageController {

    private final AccountImageService accountImageService;

    @PostMapping
    public ResponseEntity<ResAccountImageDTO> createAccountImage(
            @Valid @RequestBody ReqAccountImageDTO reqAccountImageDTO) {
        ResAccountImageDTO created = accountImageService.createAccountImage(reqAccountImageDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResAccountImageDTO> updateAccountImage(
            @PathVariable Long id,
            @Valid @RequestBody ReqAccountImageDTO reqAccountImageDTO) {
        ResAccountImageDTO updated = accountImageService.updateAccountImage(id, reqAccountImageDTO);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResAccountImageDTO> getAccountImageById(@PathVariable Long id) {
        ResAccountImageDTO res = accountImageService.getAccountImageById(id);
        return ResponseEntity.ok(res);
    }

    @GetMapping
    public ResponseEntity<List<ResAccountImageDTO>> getAllAccountImages() {
        List<ResAccountImageDTO> list = accountImageService.getAllAccountImages();
        return ResponseEntity.ok(list);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccountImage(@PathVariable Long id) {
        accountImageService.deleteAccountImage(id);
        return ResponseEntity.noContent().build();
    }
}
