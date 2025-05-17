package com.example.product.services.shipments;

import com.example.product.models.request.managers.ReqShipmentDTO;
import com.example.product.models.response.managers.ResShipmentDTO;

import java.util.List;

public interface ShipmentService {

    ResShipmentDTO createShipment(ReqShipmentDTO dto);

    ResShipmentDTO getShipmentById(Long id);

    ResShipmentDTO updateShipment(Long id, ReqShipmentDTO dto);

    void deleteShipment(Long id);

    List<ResShipmentDTO> getAllShipments();
}
