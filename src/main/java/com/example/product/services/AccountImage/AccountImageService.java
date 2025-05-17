package com.example.product.services.AccountImage;

import com.example.product.models.request.users.ReqAccountImageDTO;
import com.example.product.models.response.users.ResAccountImageDTO;

import java.util.List;

public interface AccountImageService {

    ResAccountImageDTO createAccountImage(ReqAccountImageDTO reqAccountImageDTO);

    ResAccountImageDTO updateAccountImage(Long id, ReqAccountImageDTO reqAccountImageDTO);

    void deleteAccountImage(Long id);

    ResAccountImageDTO getAccountImageById(Long id);

    List<ResAccountImageDTO> getAllAccountImages();
}
