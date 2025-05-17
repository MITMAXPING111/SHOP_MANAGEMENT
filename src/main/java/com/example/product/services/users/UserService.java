package com.example.product.services.users;

import com.example.product.entities.users.User;
import com.example.product.models.request.users.ReqUserDTO;
import com.example.product.models.response.users.ResUserDTO;

import java.util.List;

public interface UserService {
    ResUserDTO createUser(ReqUserDTO reqUserDTO);

    ResUserDTO updateUser(Long userId, ReqUserDTO reqUserDTO);

    void deleteUser(Long userId);

    ResUserDTO getUserById(Long userId);

    List<ResUserDTO> getAllUsers();

    User handleGetUserByUsername(String username);

    boolean isEmailExist(String email);

    void updateUserToken(String token, String email);

    User getUserByRefreshTokenAndEmail(String token, String email);
}
