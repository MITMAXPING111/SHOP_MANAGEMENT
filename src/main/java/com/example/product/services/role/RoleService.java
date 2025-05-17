package com.example.product.services.role;

import com.example.product.exceptions.RestResponse;
import com.example.product.models.request.users.roles.ReqRole;
import com.example.product.models.request.users.roles.ReqRoleId;

public interface RoleService {
    RestResponse findAll();

    RestResponse findById(ReqRoleId reqRoleId);

    RestResponse createOrUpdate(ReqRole req);

    RestResponse deleteById(ReqRoleId reqRoleId);
}
