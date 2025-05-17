package com.example.product.controllers;

import com.example.product.models.request.users.ReqUserDTO;
import com.example.product.models.response.users.ResUserDTO;
import com.example.product.services.users.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping
    public ResponseEntity<ResUserDTO> createUser(@Valid @RequestBody ReqUserDTO reqUserDTO) {
        String hashPassword = this.passwordEncoder.encode(reqUserDTO.getPassword());
        reqUserDTO.setPassword(hashPassword);
        ResUserDTO createdUser = userService.createUser(reqUserDTO);
        return ResponseEntity.ok(createdUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResUserDTO> updateUser(@PathVariable Long id, @Valid @RequestBody ReqUserDTO reqUserDTO) {
        ResUserDTO updatedUser = userService.updateUser(id, reqUserDTO);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResUserDTO> getUserById(@PathVariable Long id) {
        ResUserDTO user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<List<ResUserDTO>> getAllUsers() {
        List<ResUserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
}
