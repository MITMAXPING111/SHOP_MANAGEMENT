package com.example.product.services.permission;

import com.example.product.exceptions.RestResponse;
import com.example.product.models.request.users.permissions.ReqPermission;
import com.example.product.models.request.users.permissions.ReqPermissionId;

public interface PermissionService {
    RestResponse findAll();

    RestResponse findById(ReqPermissionId permissionId);

    RestResponse createOrUpdate(ReqPermission req);

    RestResponse deleteById(ReqPermissionId permissionId);
}
