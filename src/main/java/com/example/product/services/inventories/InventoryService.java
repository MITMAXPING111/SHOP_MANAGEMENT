package com.example.product.services.inventories;

import com.example.product.exceptions.errors.IdInvalidException;
import com.example.product.models.request.managers.ReqInventoryDTO;
import com.example.product.models.response.managers.ResInventoryDTO;

import java.util.List;

public interface InventoryService {
    ResInventoryDTO create(ReqInventoryDTO dto) throws IdInvalidException;

    ResInventoryDTO update(Long id, ReqInventoryDTO dto) throws IdInvalidException;

    void delete(Long id) throws IdInvalidException;

    ResInventoryDTO getById(Long id) throws IdInvalidException;

    List<ResInventoryDTO> getAll() throws IdInvalidException;
}
