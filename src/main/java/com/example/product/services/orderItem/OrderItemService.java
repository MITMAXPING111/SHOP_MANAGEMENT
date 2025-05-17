package com.example.product.services.orderItem;

import com.example.product.entities.products.Order;
import com.example.product.models.request.products.ReqOrderItemDTO;
import com.example.product.models.response.products.ResOrderItemDTO;

import java.util.List;

public interface OrderItemService {
    ResOrderItemDTO createOrderItem(ReqOrderItemDTO reqOrderItemDTO);

    ResOrderItemDTO updateOrderItem(Long id, ReqOrderItemDTO reqOrderItemDTO);

    void deleteOrderItem(Long id);

    void deleteAllByOrder(Order order);

    ResOrderItemDTO getOrderItemById(Long id);

    List<ResOrderItemDTO> getAllOrderItems();
}
