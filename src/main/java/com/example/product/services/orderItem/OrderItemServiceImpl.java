package com.example.product.services.orderItem;

import com.example.product.entities.Order;
import com.example.product.entities.OrderItem;
import com.example.product.entities.Product;
import com.example.product.models.request.ReqOrderItemDTO;
import com.example.product.models.response.ResOrderItemDTO;
import com.example.product.repositories.OrderItemRepository;
import com.example.product.repositories.OrderRepository;
import com.example.product.repositories.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderItemServiceImpl implements OrderItemService {

        @Autowired
        private OrderItemRepository orderItemRepository;

        @Autowired
        private OrderRepository orderRepository;

        @Autowired
        private ProductRepository productRepository;

        @Override
        public ResOrderItemDTO createOrderItem(ReqOrderItemDTO dto) {
                Order order = orderRepository.findById(dto.getOrderId())
                                .orElseThrow(() -> new RuntimeException(
                                                "Order not found with ID: " + dto.getOrderId()));
                Product product = productRepository.findById(dto.getProductId())
                                .orElseThrow(() -> new RuntimeException(
                                                "Product not found with ID: " + dto.getProductId()));

                OrderItem item = new OrderItem();
                item.setOrder(order);
                item.setProduct(product);
                item.setQuantity(dto.getQuantity());
                item.setUnitPrice(dto.getUnitPrice());
                item.setTotalPrice(dto.getTotalPrice());
                item.setCreatedAt(dto.getCreatedAt() != null ? dto.getCreatedAt() : LocalDateTime.now());
                item.setUpdatedAt(dto.getUpdatedAt() != null ? dto.getUpdatedAt() : LocalDateTime.now());
                item.setCreatedBy(dto.getCreatedBy());
                item.setUpdatedBy(dto.getUpdatedBy());

                return mapToResDTO(orderItemRepository.save(item));
        }

        @Override
        public ResOrderItemDTO updateOrderItem(Long id, ReqOrderItemDTO dto) {
                OrderItem item = orderItemRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("OrderItem not found with ID: " + id));

                Order order = orderRepository.findById(dto.getOrderId())
                                .orElseThrow(() -> new RuntimeException(
                                                "Order not found with ID: " + dto.getOrderId()));
                Product product = productRepository.findById(dto.getProductId())
                                .orElseThrow(() -> new RuntimeException(
                                                "Product not found with ID: " + dto.getProductId()));

                item.setOrder(order);
                item.setProduct(product);
                item.setQuantity(dto.getQuantity());
                item.setUnitPrice(dto.getUnitPrice());
                item.setTotalPrice(dto.getTotalPrice());
                item.setUpdatedAt(LocalDateTime.now());
                item.setUpdatedBy(dto.getUpdatedBy());

                return mapToResDTO(orderItemRepository.save(item));
        }

        @Override
        public void deleteOrderItem(Long id) {
                orderItemRepository.deleteById(id);
        }

        @Override
        public ResOrderItemDTO getOrderItemById(Long id) {
                return orderItemRepository.findById(id)
                                .map(this::mapToResDTO)
                                .orElseThrow(() -> new RuntimeException("OrderItem not found with ID: " + id));
        }

        @Override
        public List<ResOrderItemDTO> getAllOrderItems() {
                return orderItemRepository.findAll()
                                .stream()
                                .map(this::mapToResDTO)
                                .collect(Collectors.toList());
        }

        private ResOrderItemDTO mapToResDTO(OrderItem item) {
                return new ResOrderItemDTO(
                                item.getId(),
                                item.getOrder(),
                                item.getProduct(),
                                item.getQuantity(),
                                item.getUnitPrice(),
                                item.getTotalPrice(),
                                item.getCreatedAt(),
                                item.getUpdatedAt(),
                                item.getCreatedBy(),
                                item.getUpdatedBy());
        }
}
