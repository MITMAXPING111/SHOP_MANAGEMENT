package com.example.product.services.address;

import java.util.List;

import com.example.product.models.request.ReqAddressDTO;
import com.example.product.models.response.ResAddressDTO;

public interface AddressService {
    ResAddressDTO createAddress(ReqAddressDTO dto);

    ResAddressDTO updateAddress(Long id, ReqAddressDTO dto);

    void deleteAddress(Long id);

    ResAddressDTO getAddressById(Long id);

    List<ResAddressDTO> getAllAddresses();
}
