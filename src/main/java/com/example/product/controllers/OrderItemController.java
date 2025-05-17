package com.example.product.controllers;

import com.example.product.models.request.products.ReqOrderItemDTO;
import com.example.product.models.response.products.ResOrderItemDTO;
import com.example.product.services.orderItem.OrderItemService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order-items")
@RequiredArgsConstructor
public class OrderItemController {

    private final OrderItemService orderItemService;

    @PostMapping
    public ResponseEntity<ResOrderItemDTO> createOrderItem(@RequestBody ReqOrderItemDTO request) {
        ResOrderItemDTO created = orderItemService.createOrderItem(request);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResOrderItemDTO> updateOrderItem(@PathVariable Long id,
            @RequestBody ReqOrderItemDTO request) {
        ResOrderItemDTO updated = orderItemService.updateOrderItem(id, request);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderItem(@PathVariable Long id) {
        orderItemService.deleteOrderItem(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResOrderItemDTO> getOrderItemById(@PathVariable Long id) {
        ResOrderItemDTO response = orderItemService.getOrderItemById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ResOrderItemDTO>> getAllOrderItems() {
        List<ResOrderItemDTO> items = orderItemService.getAllOrderItems();
        return ResponseEntity.ok(items);
    }
}
