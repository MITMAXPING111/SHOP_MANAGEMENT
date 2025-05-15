package com.example.product.controllers;

import com.example.product.models.request.ReqOrderItemDTO;
import com.example.product.models.response.ResOrderItemDTO;
import com.example.product.services.orderItem.OrderItemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order-items")
public class OrderItemController {

    @Autowired
    private OrderItemService orderItemService;

    @PostMapping
    public ResponseEntity<ResOrderItemDTO> createOrderItem(@RequestBody ReqOrderItemDTO dto) {
        ResOrderItemDTO createdItem = orderItemService.createOrderItem(dto);
        return ResponseEntity.ok(createdItem);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResOrderItemDTO> updateOrderItem(@PathVariable Long id,
            @RequestBody ReqOrderItemDTO dto) {
        ResOrderItemDTO updatedItem = orderItemService.updateOrderItem(id, dto);
        return ResponseEntity.ok(updatedItem);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderItem(@PathVariable Long id) {
        orderItemService.deleteOrderItem(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResOrderItemDTO> getOrderItemById(@PathVariable Long id) {
        ResOrderItemDTO item = orderItemService.getOrderItemById(id);
        return ResponseEntity.ok(item);
    }

    @GetMapping
    public ResponseEntity<List<ResOrderItemDTO>> getAllOrderItems() {
        List<ResOrderItemDTO> items = orderItemService.getAllOrderItems();
        return ResponseEntity.ok(items);
    }
}
