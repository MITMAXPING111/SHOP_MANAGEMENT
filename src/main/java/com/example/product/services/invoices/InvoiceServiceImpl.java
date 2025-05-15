package com.example.product.services.invoices;

import com.example.product.entities.Invoice;
import com.example.product.entities.Order;
import com.example.product.models.request.ReqInvoiceDTO;
import com.example.product.models.response.ResInvoiceDTO;
import com.example.product.repositories.InvoiceRepository;
import com.example.product.repositories.OrderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public ResInvoiceDTO createInvoice(ReqInvoiceDTO dto) {
        Invoice invoice = new Invoice();
        invoice.setInvoiceNumber(dto.getInvoiceNumber());
        invoice.setIssuedDate(dto.getIssuedDate());
        invoice.setTotalAmount(dto.getTotalAmount());
        invoice.setVat(dto.getVat());
        invoice.setDiscount(dto.getDiscount());
        invoice.setPaymentMethod(dto.getPaymentMethod());
        invoice.setCreatedAt(dto.getCreatedAt() != null ? dto.getCreatedAt() : LocalDateTime.now());
        invoice.setUpdatedAt(dto.getUpdatedAt() != null ? dto.getUpdatedAt() : LocalDateTime.now());
        invoice.setCreatedBy(dto.getCreatedBy());
        invoice.setUpdatedBy(dto.getUpdatedBy());

        Order order = orderRepository.findById(dto.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + dto.getOrderId()));
        invoice.setOrder(order);

        return mapToResDTO(invoiceRepository.save(invoice));
    }

    @Override
    public ResInvoiceDTO updateInvoice(Long id, ReqInvoiceDTO dto) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found with ID: " + id));

        invoice.setInvoiceNumber(dto.getInvoiceNumber());
        invoice.setIssuedDate(dto.getIssuedDate());
        invoice.setTotalAmount(dto.getTotalAmount());
        invoice.setVat(dto.getVat());
        invoice.setDiscount(dto.getDiscount());
        invoice.setPaymentMethod(dto.getPaymentMethod());
        invoice.setUpdatedAt(LocalDateTime.now());
        invoice.setUpdatedBy(dto.getUpdatedBy());

        if (dto.getOrderId() != null) {
            Order order = orderRepository.findById(dto.getOrderId())
                    .orElseThrow(() -> new RuntimeException("Order not found with ID: " + dto.getOrderId()));
            invoice.setOrder(order);
        }

        return mapToResDTO(invoiceRepository.save(invoice));
    }

    @Override
    public void deleteInvoice(Long id) {
        invoiceRepository.deleteById(id);
    }

    @Override
    public ResInvoiceDTO getInvoiceById(Long id) {
        return invoiceRepository.findById(id)
                .map(this::mapToResDTO)
                .orElseThrow(() -> new RuntimeException("Invoice not found with ID: " + id));
    }

    @Override
    public List<ResInvoiceDTO> getAllInvoices() {
        return invoiceRepository.findAll()
                .stream()
                .map(this::mapToResDTO)
                .collect(Collectors.toList());
    }

    private ResInvoiceDTO mapToResDTO(Invoice invoice) {
        return new ResInvoiceDTO(
                invoice.getId(),
                invoice.getInvoiceNumber(),
                invoice.getIssuedDate(),
                invoice.getOrder(),
                invoice.getTotalAmount(),
                invoice.getVat(),
                invoice.getDiscount(),
                invoice.getPaymentMethod(),
                invoice.getCreatedAt(),
                invoice.getUpdatedAt(),
                invoice.getCreatedBy(),
                invoice.getUpdatedBy());
    }
}
