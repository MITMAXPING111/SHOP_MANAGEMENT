package com.example.product.services.payments;

import com.example.product.exceptions.errors.IdInvalidException;
import com.example.product.models.request.managers.ReqPaymentDTO;
import com.example.product.models.response.managers.ResPaymentDTO;

import java.util.List;

public interface PaymentService {
    ResPaymentDTO create(ReqPaymentDTO dto) throws IdInvalidException;

    ResPaymentDTO update(Long id, ReqPaymentDTO dto) throws IdInvalidException;

    void delete(Long id) throws IdInvalidException;

    ResPaymentDTO getById(Long id) throws IdInvalidException;

    List<ResPaymentDTO> getAll() throws IdInvalidException;
}
