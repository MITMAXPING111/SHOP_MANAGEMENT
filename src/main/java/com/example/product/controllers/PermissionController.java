package com.example.product.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.product.exceptions.RestResponse;
import com.example.product.models.request.users.permissions.ReqPermission;
import com.example.product.models.request.users.permissions.ReqPermissionId;
import com.example.product.services.permission.PermissionService;

@RestController
@RequestMapping("/api/v1/permissions")
public class PermissionController {
    @Autowired
    PermissionService permissionService;

    @PostMapping("")
    public ResponseEntity<?> createOrUpdate(@RequestBody ReqPermission req) {
        RestResponse result = permissionService.createOrUpdate(req);

        return new ResponseEntity<>(result, HttpStatusCode.valueOf(result.getStatusCode()));
    }

    @GetMapping("")
    public ResponseEntity<?> getAll() {
        RestResponse result = permissionService.findAll();

        return new ResponseEntity<>(result, HttpStatusCode.valueOf(result.getStatusCode()));
    }

    @PostMapping("/find-by-id")
    public ResponseEntity<?> getById(@RequestBody ReqPermissionId permissionId) {
        RestResponse result = permissionService.findById(permissionId);

        return new ResponseEntity<>(result, HttpStatusCode.valueOf(result.getStatusCode()));
    }

    @PostMapping("/delete-by-id")
    public ResponseEntity<?> deleteById(@RequestBody ReqPermissionId permissionId) {
        RestResponse result = permissionService.deleteById(permissionId);

        return new ResponseEntity<>(result, HttpStatusCode.valueOf(result.getStatusCode()));
    }
}
