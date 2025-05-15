package com.example.product.services.users;

import java.util.List;

import com.example.product.models.request.ReqUserDTO;
import com.example.product.models.response.ResUserDTO;

public interface UserService {
    ResUserDTO createUser(ReqUserDTO dto);

    ResUserDTO updateUser(Long id, ReqUserDTO dto);

    void deleteUser(Long id);

    ResUserDTO getUserById(Long id);

    List<ResUserDTO> getAllUsers();
}
