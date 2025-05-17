package com.example.product.services.invoices;

import com.example.product.entities.managers.Invoice;
import com.example.product.entities.products.Order;
import com.example.product.models.request.managers.ReqInvoiceDTO;
import com.example.product.models.response.managers.ResInvoiceDTO;
import com.example.product.repositories.InvoiceRepository;
import com.example.product.repositories.OrderRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final OrderRepository orderRepository;

    @Override
    public ResInvoiceDTO createInvoice(ReqInvoiceDTO req) {
        Order order = orderRepository.findById(req.getOrderId())
                .orElseThrow(() -> new EntityNotFoundException("Order not found with ID: " + req.getOrderId()));

        Invoice invoice = new Invoice();
        invoice.setInvoiceNumber(req.getInvoiceNumber());
        invoice.setIssuedDate(req.getIssuedDate());
        invoice.setTotalAmount(req.getTotalAmount());
        invoice.setVat(req.getVat() != null ? req.getVat() : new java.math.BigDecimal("10"));
        invoice.setDiscount(req.getDiscount());
        invoice.setPaymentMethod(req.getPaymentMethod());
        invoice.setCreatedBy(req.getCreatedBy());
        invoice.setCreatedAt(LocalDateTime.now());
        invoice.setOrder(order);

        Invoice saved = invoiceRepository.save(invoice);
        return mapToDTO(saved);
    }

    @Override
    public ResInvoiceDTO updateInvoice(Long id, ReqInvoiceDTO req) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Invoice not found with ID: " + id));

        if (req.getOrderId() != null) {
            Order order = orderRepository.findById(req.getOrderId())
                    .orElseThrow(() -> new EntityNotFoundException("Order not found with ID: " + req.getOrderId()));
            invoice.setOrder(order);
        }

        invoice.setInvoiceNumber(req.getInvoiceNumber());
        invoice.setIssuedDate(req.getIssuedDate());
        invoice.setTotalAmount(req.getTotalAmount());
        invoice.setVat(req.getVat());
        invoice.setDiscount(req.getDiscount());
        invoice.setPaymentMethod(req.getPaymentMethod());
        invoice.setUpdatedBy(req.getUpdatedBy());
        invoice.setUpdatedAt(LocalDateTime.now());

        Invoice updated = invoiceRepository.save(invoice);
        return mapToDTO(updated);
    }

    @Override
    public void deleteInvoice(Long id) {
        if (!invoiceRepository.existsById(id)) {
            throw new EntityNotFoundException("Invoice not found with ID: " + id);
        }
        invoiceRepository.deleteById(id);
    }

    @Override
    public ResInvoiceDTO getInvoiceById(Long id) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Invoice not found with ID: " + id));
        return mapToDTO(invoice);
    }

    @Override
    public List<ResInvoiceDTO> getAllInvoices() {
        return invoiceRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private ResInvoiceDTO mapToDTO(Invoice invoice) {
        return ResInvoiceDTO.builder()
                .id(invoice.getId())
                .invoiceNumber(invoice.getInvoiceNumber())
                .issuedDate(invoice.getIssuedDate())
                .totalAmount(invoice.getTotalAmount())
                .vat(invoice.getVat())
                .discount(invoice.getDiscount())
                .paymentMethod(invoice.getPaymentMethod())
                .createdBy(invoice.getCreatedBy())
                .createdAt(invoice.getCreatedAt())
                .updatedBy(invoice.getUpdatedBy())
                .updatedAt(invoice.getUpdatedAt())
                .orderId(invoice.getOrder() != null ? invoice.getOrder().getId() : null)
                .build();
    }
}
