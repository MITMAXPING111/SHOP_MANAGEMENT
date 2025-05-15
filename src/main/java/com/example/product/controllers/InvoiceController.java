package com.example.product.controllers;

import com.example.product.models.request.ReqInvoiceDTO;
import com.example.product.models.response.ResInvoiceDTO;
import com.example.product.services.invoices.InvoiceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @PostMapping
    public ResponseEntity<ResInvoiceDTO> createInvoice(@RequestBody ReqInvoiceDTO dto) {
        ResInvoiceDTO createdInvoice = invoiceService.createInvoice(dto);
        return ResponseEntity.ok(createdInvoice);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResInvoiceDTO> updateInvoice(@PathVariable Long id, @RequestBody ReqInvoiceDTO dto) {
        ResInvoiceDTO updatedInvoice = invoiceService.updateInvoice(id, dto);
        return ResponseEntity.ok(updatedInvoice);
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
