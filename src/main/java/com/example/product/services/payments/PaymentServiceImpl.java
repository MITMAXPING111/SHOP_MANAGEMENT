package com.example.product.services.payments;

import com.example.product.entities.managers.Payment;
import com.example.product.entities.products.Order;
import com.example.product.exceptions.errors.IdInvalidException;
import com.example.product.models.request.managers.ReqPaymentDTO;
import com.example.product.models.response.managers.ResPaymentDTO;
import com.example.product.repositories.OrderRepository;
import com.example.product.repositories.PaymentRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    @Override
    public ResPaymentDTO create(ReqPaymentDTO reqPaymentDTO) {
        Payment payment = new Payment();
        payment.setPaymentDate(reqPaymentDTO.getPaymentDate());
        payment.setPaymentMethod(reqPaymentDTO.getPaymentMethod());
        payment.setAmount(reqPaymentDTO.getAmount());
        payment.setTransactionId(reqPaymentDTO.getTransactionId());
        payment.setStatus(reqPaymentDTO.getStatus());

        // Set created & updated audit info
        LocalDateTime now = LocalDateTime.now();
        payment.setCreatedAt(now);
        payment.setCreatedBy(reqPaymentDTO.getCreatedBy());

        Payment saved = paymentRepository.save(payment);
        return mapEntityToDto(saved);
    }

    @Override
    public ResPaymentDTO getById(Long id) throws IdInvalidException {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new IdInvalidException("Payment not found with ID: " + id));
        return mapEntityToDto(payment);
    }

    @Override
    public List<ResPaymentDTO> getAll() {
        return paymentRepository.findAll()
                .stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ResPaymentDTO update(Long id, ReqPaymentDTO reqPaymentDTO) throws IdInvalidException {
        Payment existing = paymentRepository.findById(id)
                .orElseThrow(() -> new IdInvalidException("Payment not found with ID: " + id));

        existing.setPaymentDate(reqPaymentDTO.getPaymentDate());
        existing.setPaymentMethod(reqPaymentDTO.getPaymentMethod());
        existing.setAmount(reqPaymentDTO.getAmount());
        existing.setTransactionId(reqPaymentDTO.getTransactionId());
        existing.setStatus(reqPaymentDTO.getStatus());

        // Update audit info
        existing.setUpdatedAt(LocalDateTime.now());
        existing.setUpdatedBy(reqPaymentDTO.getUpdatedBy());

        return mapEntityToDto(paymentRepository.save(existing));
    }

    @Override
    public void delete(Long id) throws IdInvalidException {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new IdInvalidException("Payment not found with ID: " + id));
        paymentRepository.delete(payment);
    }

    private ResPaymentDTO mapEntityToDto(Payment entity) {
        return ResPaymentDTO.builder()
                .id(entity.getId())
                .paymentDate(entity.getPaymentDate())
                .paymentMethod(entity.getPaymentMethod())
                .amount(entity.getAmount())
                .transactionId(entity.getTransactionId())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .createdBy(entity.getCreatedBy())
                .updatedAt(entity.getUpdatedAt())
                .updatedBy(entity.getUpdatedBy())
                .orderIds(entity.getOrders()
                        .stream()
                        .map(Order::getId)
                        .collect(Collectors.toList()))
                .build();
    }
}
