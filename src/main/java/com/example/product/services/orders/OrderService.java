package com.example.product.services.orders;

import com.example.product.models.request.products.ReqOrderDTO;
import com.example.product.models.response.products.ResOrderDTO;

import java.util.List;

public interface OrderService {
    ResOrderDTO createOrder(ReqOrderDTO reqOrderDTO);

    ResOrderDTO getOrderById(Long id);

    List<ResOrderDTO> getAllOrders();

    ResOrderDTO updateOrder(Long id, ReqOrderDTO reqOrderDTO);

    void deleteOrder(Long id);
}
