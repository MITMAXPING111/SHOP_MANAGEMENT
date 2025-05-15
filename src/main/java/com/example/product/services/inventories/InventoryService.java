package com.example.product.services.inventories;

import java.util.List;

import com.example.product.models.request.ReqInventoryDTO;
import com.example.product.models.response.ResInventoryDTO;

public interface InventoryService {
    ResInventoryDTO createInventory(ReqInventoryDTO dto);

    ResInventoryDTO updateInventory(Long id, ReqInventoryDTO dto);

    void deleteInventory(Long id);

    ResInventoryDTO getInventoryById(Long id);

    List<ResInventoryDTO> getAllInventories();
}
