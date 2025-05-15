package com.example.product.services.invoices;

import java.util.List;

import com.example.product.models.request.ReqInvoiceDTO;
import com.example.product.models.response.ResInvoiceDTO;

public interface InvoiceService {
    ResInvoiceDTO createInvoice(ReqInvoiceDTO dto);

    ResInvoiceDTO updateInvoice(Long id, ReqInvoiceDTO dto);

    void deleteInvoice(Long id);

    ResInvoiceDTO getInvoiceById(Long id);

    List<ResInvoiceDTO> getAllInvoices();
}
