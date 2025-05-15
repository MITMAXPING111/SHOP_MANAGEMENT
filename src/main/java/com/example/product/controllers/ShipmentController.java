package com.example.product.controllers;

import com.example.product.models.request.ReqShipmentDTO;
import com.example.product.models.response.ResShipmentDTO;
import com.example.product.services.shipments.ShipmentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shipments")
public class ShipmentController {

    @Autowired
    private ShipmentService shipmentService;

    @PostMapping
    public ResponseEntity<ResShipmentDTO> createShipment(@RequestBody ReqShipmentDTO dto) {
        ResShipmentDTO createdShipment = shipmentService.createShipment(dto);
        return ResponseEntity.ok(createdShipment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResShipmentDTO> updateShipment(@PathVariable Long id,
            @RequestBody ReqShipmentDTO dto) {
        ResShipmentDTO updatedShipment = shipmentService.updateShipment(id, dto);
        return ResponseEntity.ok(updatedShipment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShipment(@PathVariable Long id) {
        shipmentService.deleteShipment(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResShipmentDTO> getShipmentById(@PathVariable Long id) {
        ResShipmentDTO shipment = shipmentService.getShipmentById(id);
        return ResponseEntity.ok(shipment);
    }

    @GetMapping
    public ResponseEntity<List<ResShipmentDTO>> getAllShipments() {
        List<ResShipmentDTO> shipments = shipmentService.getAllShipments();
        return ResponseEntity.ok(shipments);
    }
}
