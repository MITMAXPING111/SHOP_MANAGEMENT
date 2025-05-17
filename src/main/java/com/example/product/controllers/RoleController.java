package com.example.product.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.product.exceptions.RestResponse;
import com.example.product.models.request.users.roles.ReqRole;
import com.example.product.models.request.users.roles.ReqRoleId;
import com.example.product.services.role.RoleService;

@RestController
@RequestMapping("/api/v1/roles")
public class RoleController {
    @Autowired
    RoleService roleService;

    @PostMapping("")
    public ResponseEntity<?> createOrUpdate(@RequestBody ReqRole req) {
        RestResponse result = roleService.createOrUpdate(req);

        return new ResponseEntity<>(result, HttpStatusCode.valueOf(result.getStatusCode()));
    }

    @GetMapping("")
    public ResponseEntity<?> getAll() {
        RestResponse result = roleService.findAll();

        return new ResponseEntity<>(result, HttpStatusCode.valueOf(result.getStatusCode()));
    }

    @PostMapping("/find-by-id")
    public ResponseEntity<?> getById(@RequestBody ReqRoleId roleId) {
        RestResponse result = roleService.findById(roleId);

        return new ResponseEntity<>(result, HttpStatusCode.valueOf(result.getStatusCode()));
    }

    @PostMapping("/delete-by-id")
    public ResponseEntity<?> deleteById(@RequestBody ReqRoleId roleId) {
        RestResponse result = roleService.deleteById(roleId);

        return new ResponseEntity<>(result, HttpStatusCode.valueOf(result.getStatusCode()));
    }
}
