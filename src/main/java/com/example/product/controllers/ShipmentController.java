package com.example.product.controllers;

import com.example.product.models.request.managers.ReqShipmentDTO;
import com.example.product.models.response.managers.ResShipmentDTO;
import com.example.product.services.shipments.ShipmentService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/shipments")
@RequiredArgsConstructor
public class ShipmentController {

    private final ShipmentService shipmentService;

    @PostMapping
    public ResponseEntity<ResShipmentDTO> createShipment(@RequestBody ReqShipmentDTO request) {
        ResShipmentDTO response = shipmentService.createShipment(request);
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ResShipmentDTO>> getAllShipments() {
        List<ResShipmentDTO> response = shipmentService.getAllShipments();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResShipmentDTO> getShipmentById(@PathVariable Long id) {
        ResShipmentDTO response = shipmentService.getShipmentById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResShipmentDTO> updateShipment(@PathVariable Long id, @RequestBody ReqShipmentDTO request) {
        ResShipmentDTO response = shipmentService.updateShipment(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShipment(@PathVariable Long id) {
        shipmentService.deleteShipment(id);
        return ResponseEntity.noContent().build();
    }
}
