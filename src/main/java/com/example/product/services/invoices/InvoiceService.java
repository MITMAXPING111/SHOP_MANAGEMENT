package com.example.product.services.invoices;

import com.example.product.models.request.managers.ReqInvoiceDTO;
import com.example.product.models.response.managers.ResInvoiceDTO;

import java.util.List;

public interface InvoiceService {
    ResInvoiceDTO createInvoice(ReqInvoiceDTO reqInvoiceDTO);

    ResInvoiceDTO updateInvoice(Long id, ReqInvoiceDTO reqInvoiceDTO);

    void deleteInvoice(Long id);

    ResInvoiceDTO getInvoiceById(Long id);

    List<ResInvoiceDTO> getAllInvoices();
}
