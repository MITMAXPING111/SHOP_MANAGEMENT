package com.example.product.controllers;

import com.example.product.models.request.managers.ReqInvoiceDTO;
import com.example.product.models.response.managers.ResInvoiceDTO;
import com.example.product.services.invoices.InvoiceService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/invoices")
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceService invoiceService;

    @PostMapping
    public ResponseEntity<ResInvoiceDTO> createInvoice(@RequestBody ReqInvoiceDTO reqInvoiceDTO) {
        ResInvoiceDTO created = invoiceService.createInvoice(reqInvoiceDTO);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResInvoiceDTO> updateInvoice(
            @PathVariable Long id,
            @RequestBody ReqInvoiceDTO reqInvoiceDTO) {
        ResInvoiceDTO updated = invoiceService.updateInvoice(id, reqInvoiceDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvoice(@PathVariable Long id) {
        invoiceService.deleteInvoice(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResInvoiceDTO> getInvoiceById(@PathVariable Long id) {
        ResInvoiceDTO invoice = invoiceService.getInvoiceById(id);
        return ResponseEntity.ok(invoice);
    }

    @GetMapping
    public ResponseEntity<List<ResInvoiceDTO>> getAllInvoices() {
        List<ResInvoiceDTO> invoices = invoiceService.getAllInvoices();
        return ResponseEntity.ok(invoices);
    }
}
