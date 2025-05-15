package com.example.product.services.orders;

import com.example.product.entities.Customer;
import com.example.product.entities.Order;
import com.example.product.models.request.ReqOrderDTO;
import com.example.product.models.response.ResOrderDTO;
import com.example.product.repositories.CustomerRepository;
import com.example.product.repositories.OrderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public ResOrderDTO createOrder(ReqOrderDTO dto) {
        Order order = new Order();

        Customer customer = customerRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + dto.getCustomerId()));

        order.setCustomer(customer);
        order.setOrderDate(dto.getOrderDate());
        order.setStatus(dto.getStatus());
        order.setTotalAmount(dto.getTotalAmount());
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());
        order.setCreatedBy(dto.getCreatedBy());
        order.setUpdatedBy(dto.getUpdatedBy());

        return mapToResDTO(orderRepository.save(order));
    }

    @Override
    public ResOrderDTO updateOrder(Long id, ReqOrderDTO dto) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + id));

        Customer customer = customerRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + dto.getCustomerId()));

        order.setCustomer(customer);
        order.setOrderDate(dto.getOrderDate());
        order.setStatus(dto.getStatus());
        order.setTotalAmount(dto.getTotalAmount());
        order.setUpdatedAt(LocalDateTime.now());
        order.setUpdatedBy(dto.getUpdatedBy());

        return mapToResDTO(orderRepository.save(order));
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public ResOrderDTO getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + id));
        return mapToResDTO(order);
    }

    @Override
    public List<ResOrderDTO> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(this::mapToResDTO)
                .collect(Collectors.toList());
    }

    private ResOrderDTO mapToResDTO(Order order) {
        ResOrderDTO dto = new ResOrderDTO();
        dto.setId(order.getId());
        dto.setCustomer(order.getCustomer());
        dto.setOrderDate(order.getOrderDate());
        dto.setStatus(order.getStatus());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setCreatedAt(order.getCreatedAt());
        dto.setUpdatedAt(order.getUpdatedAt());
        dto.setCreatedBy(order.getCreatedBy());
        dto.setUpdatedBy(order.getUpdatedBy());
        return dto;
    }
}
