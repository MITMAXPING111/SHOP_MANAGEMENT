package com.example.product.services.shipments;

import com.example.product.constants.StatusShipment;
import com.example.product.entities.managers.Shipment;
import com.example.product.entities.products.Order;
import com.example.product.models.request.managers.ReqShipmentDTO;
import com.example.product.models.response.managers.ResShipmentDTO;
import com.example.product.repositories.OrderRepository;
import com.example.product.repositories.ShipmentRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShipmentServiceImpl implements ShipmentService {

    private final ShipmentRepository shipmentRepository;
    private final OrderRepository orderRepository;

    @Override
    public ResShipmentDTO createShipment(ReqShipmentDTO dto) {
        Shipment shipment = new Shipment();
        shipment.setTrackingNumber(dto.getTrackingNumber());
        shipment.setCarrier(dto.getCarrier());
        shipment.setShippedDate(dto.getShippedDate());
        shipment.setDeliveredDate(dto.getDeliveredDate());
        shipment.setStatus(dto.getStatus() != null ? dto.getStatus() : StatusShipment.PENDING);

        shipment.setCreatedAt(LocalDateTime.now());
        shipment.setCreatedBy(dto.getCreatedBy());

        Order order = orderRepository.findById(dto.getOrderId())
                .orElseThrow(() -> new EntityNotFoundException("Order not found with id: " + dto.getOrderId()));
        shipment.setOrder(order);

        Shipment saved = shipmentRepository.save(shipment);
        return mapToDTO(saved);
    }

    @Override
    public ResShipmentDTO getShipmentById(Long id) {
        Shipment shipment = shipmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Shipment not found with id: " + id));
        return mapToDTO(shipment);
    }

    @Override
    public ResShipmentDTO updateShipment(Long id, ReqShipmentDTO dto) {
        Shipment shipment = shipmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Shipment not found with id: " + id));

        shipment.setTrackingNumber(dto.getTrackingNumber());
        shipment.setCarrier(dto.getCarrier());
        shipment.setShippedDate(dto.getShippedDate());
        shipment.setDeliveredDate(dto.getDeliveredDate());
        shipment.setStatus(dto.getStatus());

        shipment.setUpdatedAt(LocalDateTime.now());
        shipment.setUpdatedBy(dto.getUpdatedBy());

        if (dto.getOrderId() != null) {
            Order order = orderRepository.findById(dto.getOrderId())
                    .orElseThrow(() -> new EntityNotFoundException("Order not found with id: " + dto.getOrderId()));
            shipment.setOrder(order);
        }

        Shipment updated = shipmentRepository.save(shipment);
        return mapToDTO(updated);
    }

    @Override
    public void deleteShipment(Long id) {
        Shipment shipment = shipmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Shipment not found with id: " + id));
        shipmentRepository.delete(shipment);
    }

    @Override
    public List<ResShipmentDTO> getAllShipments() {
        return shipmentRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private ResShipmentDTO mapToDTO(Shipment shipment) {
        return ResShipmentDTO.builder()
                .id(shipment.getId())
                .trackingNumber(shipment.getTrackingNumber())
                .carrier(shipment.getCarrier())
                .shippedDate(shipment.getShippedDate())
                .deliveredDate(shipment.getDeliveredDate())
                .status(shipment.getStatus())
                .createdAt(shipment.getCreatedAt())
                .updatedAt(shipment.getUpdatedAt())
                .createdBy(shipment.getCreatedBy())
                .updatedBy(shipment.getUpdatedBy())
                .orderId(shipment.getOrder() != null ? shipment.getOrder().getId() : null)
                .build();
    }
}
