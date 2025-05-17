package com.example.product.controllers;

import com.example.product.exceptions.errors.IdInvalidException;
import com.example.product.models.request.managers.ReqPaymentDTO;
import com.example.product.models.response.managers.ResPaymentDTO;
import com.example.product.services.payments.PaymentService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<ResPaymentDTO> createPayment(@RequestBody ReqPaymentDTO reqPaymentDTO)
            throws IdInvalidException {
        ResPaymentDTO response = paymentService.create(reqPaymentDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResPaymentDTO> getPaymentById(@PathVariable Long id) throws IdInvalidException {
        ResPaymentDTO response = paymentService.getById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ResPaymentDTO>> getAllPayments() throws IdInvalidException {
        List<ResPaymentDTO> payments = paymentService.getAll();
        return ResponseEntity.ok(payments);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResPaymentDTO> updatePayment(
            @PathVariable Long id,
            @RequestBody ReqPaymentDTO reqPaymentDTO) throws IdInvalidException {
        ResPaymentDTO response = paymentService.update(id, reqPaymentDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable Long id) throws IdInvalidException {
        paymentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
