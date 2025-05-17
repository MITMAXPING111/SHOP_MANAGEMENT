package com.example.product.services.addresses;

import com.example.product.models.request.users.ReqAddressDTO;
import com.example.product.models.response.users.ResAddressDTO;

import java.util.List;

public interface AddressService {
    ResAddressDTO createAddress(ReqAddressDTO dto);

    ResAddressDTO updateAddress(Long id, ReqAddressDTO dto);

    void deleteAddress(Long id);

    ResAddressDTO getAddressById(Long id);

    List<ResAddressDTO> getAllAddresses();
}
