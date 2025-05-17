package com.example.product.services.orderItem;

import com.example.product.entities.products.Order;
import com.example.product.entities.products.OrderItem;
import com.example.product.entities.products.ProductVariant;
import com.example.product.models.request.products.ReqOrderItemDTO;
import com.example.product.models.response.products.ResOrderItemDTO;
import com.example.product.repositories.OrderItemRepository;
import com.example.product.repositories.OrderRepository;
import com.example.product.repositories.ProductVariantRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final ProductVariantRepository productVariantRepository;

    @Override
    public ResOrderItemDTO createOrderItem(ReqOrderItemDTO req) {
        Order order = orderRepository.findById(req.getOrderId())
                .orElseThrow(() -> new EntityNotFoundException("Order not found with ID: " + req.getOrderId()));

        OrderItem orderItem = new OrderItem();
        orderItem.setQuantity(req.getQuantity());
        orderItem.setUnitPrice(req.getUnitPrice());
        orderItem.setTotalPrice(req.getTotalPrice());
        orderItem.setCreatedBy(req.getCreatedBy());
        orderItem.setCreatedAt(LocalDateTime.now());
        orderItem.setOrder(order);

        if (req.getProductVariantIds() != null) {
            List<ProductVariant> variants = productVariantRepository.findAllById(req.getProductVariantIds());
            variants.forEach(variant -> variant.setOrderItem(orderItem));
            orderItem.setProductVariants(variants);
        }

        OrderItem saved = orderItemRepository.save(orderItem);
        return mapToDTO(saved);
    }

    @Override
    public ResOrderItemDTO updateOrderItem(Long id, ReqOrderItemDTO req) {
        OrderItem orderItem = orderItemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("OrderItem not found with ID: " + id));

        if (req.getOrderId() != null) {
            Order order = orderRepository.findById(req.getOrderId())
                    .orElseThrow(() -> new EntityNotFoundException("Order not found with ID: " + req.getOrderId()));
            orderItem.setOrder(order);
        }

        orderItem.setQuantity(req.getQuantity());
        orderItem.setUnitPrice(req.getUnitPrice());
        orderItem.setTotalPrice(req.getTotalPrice());
        orderItem.setUpdatedBy(req.getUpdatedBy());
        orderItem.setUpdatedAt(LocalDateTime.now());

        if (req.getProductVariantIds() != null) {
            List<ProductVariant> variants = productVariantRepository.findAllById(req.getProductVariantIds());
            orderItem.getProductVariants().clear();
            variants.forEach(variant -> variant.setOrderItem(orderItem));
            orderItem.getProductVariants().addAll(variants);
        }

        OrderItem updated = orderItemRepository.save(orderItem);
        return mapToDTO(updated);
    }

    @Override
    public void deleteOrderItem(Long id) {
        if (!orderItemRepository.existsById(id)) {
            throw new EntityNotFoundException("OrderItem not found with ID: " + id);
        }
        orderItemRepository.deleteById(id);
    }

    @Override
    public ResOrderItemDTO getOrderItemById(Long id) {
        OrderItem orderItem = orderItemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("OrderItem not found with ID: " + id));
        return mapToDTO(orderItem);
    }

    @Override
    public List<ResOrderItemDTO> getAllOrderItems() {
        return orderItemRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private ResOrderItemDTO mapToDTO(OrderItem orderItem) {
        List<Long> variantIds = orderItem.getProductVariants() != null
                ? orderItem.getProductVariants().stream()
                        .map(ProductVariant::getId)
                        .collect(Collectors.toList())
                : null;

        return ResOrderItemDTO.builder()
                .id(orderItem.getId())
                .quantity(orderItem.getQuantity())
                .unitPrice(orderItem.getUnitPrice())
                .totalPrice(orderItem.getTotalPrice())
                .createdBy(orderItem.getCreatedBy())
                .createdAt(orderItem.getCreatedAt())
                .updatedBy(orderItem.getUpdatedBy())
                .updatedAt(orderItem.getUpdatedAt())
                .orderId(orderItem.getOrder() != null ? orderItem.getOrder().getId() : null)
                .productVariantIds(variantIds)
                .build();
    }

    @Override
    public void deleteAllByOrder(Order order) {
        orderItemRepository.deleteAllByOrder(order);
    }
}
