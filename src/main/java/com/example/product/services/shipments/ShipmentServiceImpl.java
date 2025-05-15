package com.example.product.services.shipments;

import com.example.product.entities.Order;
import com.example.product.entities.Shipment;
import com.example.product.models.request.ReqShipmentDTO;
import com.example.product.models.response.ResShipmentDTO;
import com.example.product.repositories.OrderRepository;
import com.example.product.repositories.ShipmentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShipmentServiceImpl implements ShipmentService {

    @Autowired
    private ShipmentRepository shipmentRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public ResShipmentDTO createShipment(ReqShipmentDTO dto) {
        Shipment shipment = new Shipment();
        shipment.setTrackingNumber(dto.getTrackingNumber());
        shipment.setCarrier(dto.getCarrier());
        shipment.setShippedDate(dto.getShippedDate());
        shipment.setDeliveredDate(dto.getDeliveredDate());
        shipment.setStatus(dto.getStatus());
        shipment.setCreatedAt(dto.getCreatedAt() != null ? dto.getCreatedAt() : LocalDateTime.now());
        shipment.setUpdatedAt(dto.getUpdatedAt() != null ? dto.getUpdatedAt() : LocalDateTime.now());
        shipment.setCreatedBy(dto.getCreatedBy());
        shipment.setUpdatedBy(dto.getUpdatedBy());

        Order order = orderRepository.findById(dto.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + dto.getOrderId()));
        shipment.setOrder(order);

        return mapToResDTO(shipmentRepository.save(shipment));
    }

    @Override
    public ResShipmentDTO updateShipment(Long id, ReqShipmentDTO dto) {
        Shipment shipment = shipmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shipment not found with ID: " + id));

        shipment.setTrackingNumber(dto.getTrackingNumber());
        shipment.setCarrier(dto.getCarrier());
        shipment.setShippedDate(dto.getShippedDate());
        shipment.setDeliveredDate(dto.getDeliveredDate());
        shipment.setStatus(dto.getStatus());
        shipment.setUpdatedAt(LocalDateTime.now());
        shipment.setUpdatedBy(dto.getUpdatedBy());

        if (dto.getOrderId() != null) {
            Order order = orderRepository.findById(dto.getOrderId())
                    .orElseThrow(() -> new RuntimeException("Order not found with ID: " + dto.getOrderId()));
            shipment.setOrder(order);
        }

        return mapToResDTO(shipmentRepository.save(shipment));
    }

    @Override
    public void deleteShipment(Long id) {
        shipmentRepository.deleteById(id);
    }

    @Override
    public ResShipmentDTO getShipmentById(Long id) {
        return shipmentRepository.findById(id)
                .map(this::mapToResDTO)
                .orElseThrow(() -> new RuntimeException("Shipment not found with ID: " + id));
    }

    @Override
    public List<ResShipmentDTO> getAllShipments() {
        return shipmentRepository.findAll()
                .stream()
                .map(this::mapToResDTO)
                .collect(Collectors.toList());
    }

    private ResShipmentDTO mapToResDTO(Shipment shipment) {
        return new ResShipmentDTO(
                shipment.getId(),
                shipment.getTrackingNumber(),
                shipment.getCarrier(),
                shipment.getShippedDate(),
                shipment.getDeliveredDate(),
                shipment.getOrder(),
                shipment.getStatus(),
                shipment.getCreatedAt(),
                shipment.getUpdatedAt(),
                shipment.getCreatedBy(),
                shipment.getUpdatedBy());
    }
}
