package com.example.product.services.orders;

import java.util.List;

import com.example.product.models.request.ReqOrderDTO;
import com.example.product.models.response.ResOrderDTO;

public interface OrderService {
    ResOrderDTO createOrder(ReqOrderDTO dto);

    ResOrderDTO updateOrder(Long id, ReqOrderDTO dto);

    void deleteOrder(Long id);

    ResOrderDTO getOrderById(Long id);

    List<ResOrderDTO> getAllOrders();
}