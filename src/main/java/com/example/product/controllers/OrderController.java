package com.example.product.controllers;

import com.example.product.models.request.ReqOrderDTO;
import com.example.product.models.response.ResOrderDTO;
import com.example.product.services.orders.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<ResOrderDTO> createOrder(@RequestBody ReqOrderDTO dto) {
        ResOrderDTO createdOrder = orderService.createOrder(dto);
        return ResponseEntity.ok(createdOrder);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResOrderDTO> updateOrder(@PathVariable Long id, @RequestBody ReqOrderDTO dto) {
        ResOrderDTO updatedOrder = orderService.updateOrder(id, dto);
        return ResponseEntity.ok(updatedOrder);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResOrderDTO> getOrderById(@PathVariable Long id) {
        ResOrderDTO order = orderService.getOrderById(id);
        return ResponseEntity.ok(order);
    }

    @GetMapping
    public ResponseEntity<List<ResOrderDTO>> getAllOrders() {
        List<ResOrderDTO> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }
}
