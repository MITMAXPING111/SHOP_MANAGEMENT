package com.example.product.controllers;

import com.example.product.models.request.products.ReqOrderDTO;
import com.example.product.models.response.products.ResOrderDTO;
import com.example.product.services.orders.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<ResOrderDTO> createOrder(@RequestBody ReqOrderDTO reqOrderDTO) {
        ResOrderDTO createdOrder = orderService.createOrder(reqOrderDTO);
        return ResponseEntity.ok(createdOrder);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResOrderDTO> updateOrder(@PathVariable Long id, @RequestBody ReqOrderDTO reqOrderDTO) {
        ResOrderDTO updatedOrder = orderService.updateOrder(id, reqOrderDTO);
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
