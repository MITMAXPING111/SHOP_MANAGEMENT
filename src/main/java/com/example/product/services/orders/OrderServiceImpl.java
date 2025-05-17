package com.example.product.services.orders;

import com.example.product.constants.StatusOrder;
import com.example.product.entities.products.Order;
import com.example.product.entities.products.OrderItem;
import com.example.product.entities.managers.Payment;
import com.example.product.entities.users.Address;
import com.example.product.entities.users.Customer;
import com.example.product.models.request.products.ReqOrderDTO;
import com.example.product.models.request.products.ReqOrderItemDTO;
import com.example.product.models.response.products.ResOrderDTO;
import com.example.product.models.response.products.ResOrderItemDTO;
import com.example.product.repositories.AddressRepository;
import com.example.product.repositories.CustomerRepository;
import com.example.product.repositories.OrderItemRepository;
import com.example.product.repositories.OrderRepository;
import com.example.product.repositories.PaymentRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final AddressRepository addressRepository;
    private final CustomerRepository customerRepository;
    private final PaymentRepository paymentRepository;
    private final OrderItemRepository orderItemRepository;

    @Override
    public ResOrderDTO createOrder(ReqOrderDTO reqOrderDTO) {
        Order order = new Order();

        order.setStatus(StatusOrder.PENDING);
        order.setTotalAmount(reqOrderDTO.getTotalAmount());
        order.setCreatedAt(LocalDateTime.now());
        order.setCreatedBy(reqOrderDTO.getCreatedBy());

        // Set address
        addressRepository.findById(reqOrderDTO.getAddressId())
                .ifPresent(order::setAddress);

        // Set customer
        customerRepository.findById(reqOrderDTO.getCustomerId())
                .ifPresent(order::setCustomer);

        // Set payment
        paymentRepository.findById(reqOrderDTO.getPaymentId())
                .ifPresent(order::setPayment);

        // Save order first to get ID for orderItems
        orderRepository.save(order);

        // Save order items
        if (reqOrderDTO.getOrderItems() != null && !reqOrderDTO.getOrderItems().isEmpty()) {
            List<OrderItem> orderItems = reqOrderDTO.getOrderItems().stream().map(dto -> {
                OrderItem item = new OrderItem();
                item.setQuantity(dto.getQuantity());
                item.setUnitPrice(dto.getUnitPrice());
                item.setTotalPrice(dto.getTotalPrice());
                item.setCreatedAt(LocalDateTime.now());
                item.setCreatedBy(dto.getCreatedBy());
                item.setOrder(order);
                // Here you should set ProductVariants by IDs if needed (omitted for brevity)
                return orderItemRepository.save(item);
            }).collect(Collectors.toList());

            order.setOrderItems(orderItems);
        }

        return mapToResOrderDTO(order);
    }

    @Override
    public ResOrderDTO getOrderById(Long id) {
        return orderRepository.findById(id)
                .map(this::mapToResOrderDTO)
                .orElseThrow(() -> new RuntimeException("Order not found with id " + id));
    }

    @Override
    public List<ResOrderDTO> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(this::mapToResOrderDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ResOrderDTO updateOrder(Long id, ReqOrderDTO reqOrderDTO) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id " + id));

        order.setTotalAmount(reqOrderDTO.getTotalAmount());
        order.setUpdatedAt(LocalDateTime.now());
        order.setUpdatedBy(reqOrderDTO.getUpdatedBy());

        // Update address if present
        if (reqOrderDTO.getAddressId() != null) {
            addressRepository.findById(reqOrderDTO.getAddressId())
                    .ifPresent(order::setAddress);
        }

        // Update customer if present
        if (reqOrderDTO.getCustomerId() != null) {
            customerRepository.findById(reqOrderDTO.getCustomerId())
                    .ifPresent(order::setCustomer);
        }

        // Update payment if present
        if (reqOrderDTO.getPaymentId() != null) {
            paymentRepository.findById(reqOrderDTO.getPaymentId())
                    .ifPresent(order::setPayment);
        }

        // Update order items if provided (simplified: remove old and add new)
        if (reqOrderDTO.getOrderItems() != null) {
            // Remove existing items
            order.getOrderItems().clear();
            orderItemRepository.deleteAllByOrder(order);

            // Add new items
            List<OrderItem> updatedItems = reqOrderDTO.getOrderItems().stream().map(dto -> {
                OrderItem item = new OrderItem();
                item.setQuantity(dto.getQuantity());
                item.setUnitPrice(dto.getUnitPrice());
                item.setTotalPrice(dto.getTotalPrice());
                item.setCreatedAt(dto.getCreatedAt() != null ? dto.getCreatedAt() : LocalDateTime.now());
                item.setCreatedBy(dto.getCreatedBy());
                item.setOrder(order);
                return orderItemRepository.save(item);
            }).collect(Collectors.toList());

            order.setOrderItems(updatedItems);
        }

        orderRepository.save(order);
        return mapToResOrderDTO(order);
    }

    @Override
    public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id " + id));
        orderRepository.delete(order);
    }

    private ResOrderDTO mapToResOrderDTO(Order order) {
        List<ResOrderItemDTO> resItems = order.getOrderItems().stream().map(item -> ResOrderItemDTO.builder()
                .id(item.getId())
                .quantity(item.getQuantity())
                .unitPrice(item.getUnitPrice())
                .totalPrice(item.getTotalPrice())
                .createdAt(item.getCreatedAt())
                .updatedAt(item.getUpdatedAt())
                .createdBy(item.getCreatedBy())
                .updatedBy(item.getUpdatedBy())
                .orderId(order.getId())
                .productVariantIds(item.getProductVariants() != null
                        ? item.getProductVariants().stream().map(pv -> pv.getId()).toList()
                        : List.of())
                .build()).toList();

        return ResOrderDTO.builder()
                .id(order.getId())
                .status(order.getStatus())
                .totalAmount(order.getTotalAmount())
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .createdBy(order.getCreatedBy())
                .updatedBy(order.getUpdatedBy())
                .addressId(order.getAddress() != null ? order.getAddress().getId() : null)
                .customerId(order.getCustomer() != null ? order.getCustomer().getId() : null)
                .paymentId(order.getPayment() != null ? order.getPayment().getId() : null)
                .invoiceId(order.getInvoice() != null ? order.getInvoice().getId() : null)
                .shipmentId(order.getShipment() != null ? order.getShipment().getId() : null)
                .orderItems(resItems)
                .build();
    }
}
