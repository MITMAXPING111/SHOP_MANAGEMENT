package com.example.product.services.orderItem;

import java.util.List;

import com.example.product.models.request.ReqOrderItemDTO;
import com.example.product.models.response.ResOrderItemDTO;

public interface OrderItemService {
    ResOrderItemDTO createOrderItem(ReqOrderItemDTO dto);

    ResOrderItemDTO updateOrderItem(Long id, ReqOrderItemDTO dto);

    void deleteOrderItem(Long id);

    ResOrderItemDTO getOrderItemById(Long id);

    List<ResOrderItemDTO> getAllOrderItems();
}
