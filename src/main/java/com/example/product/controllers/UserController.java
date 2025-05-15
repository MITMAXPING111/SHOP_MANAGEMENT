package com.example.product.controllers;

import com.example.product.models.request.ReqUserDTO;
import com.example.product.models.response.ResUserDTO;
import com.example.product.services.users.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<ResUserDTO> createUser(@RequestBody ReqUserDTO dto) {
        ResUserDTO createdUser = userService.createUser(dto);
        return ResponseEntity.ok(createdUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResUserDTO> updateUser(@PathVariable Long id,
            @RequestBody ReqUserDTO dto) {
        ResUserDTO updatedUser = userService.updateUser(id, dto);
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
