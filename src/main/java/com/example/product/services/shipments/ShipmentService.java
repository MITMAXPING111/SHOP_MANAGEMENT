package com.example.product.services.shipments;

import java.util.List;

import com.example.product.models.request.ReqShipmentDTO;
import com.example.product.models.response.ResShipmentDTO;

public interface ShipmentService {
    ResShipmentDTO createShipment(ReqShipmentDTO dto);

    ResShipmentDTO updateShipment(Long id, ReqShipmentDTO dto);

    void deleteShipment(Long id);

    ResShipmentDTO getShipmentById(Long id);

    List<ResShipmentDTO> getAllShipments();
}
