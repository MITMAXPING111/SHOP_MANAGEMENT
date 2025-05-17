package com.example.product.services.role;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.product.entities.users.Permission;
import com.example.product.entities.users.Role;
import com.example.product.exceptions.RestResponse;
import com.example.product.models.request.users.permissions.ReqPermissionId;
import com.example.product.models.request.users.roles.ReqRole;
import com.example.product.models.request.users.roles.ReqRoleId;
import com.example.product.models.response.users.permissions.ResPermission;
import com.example.product.models.response.users.roles.ResRole;
import com.example.product.repositories.PermissionRepo;
import com.example.product.repositories.RoleRepo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleRepo roleRepo;
    @Autowired
    PermissionRepo permissionRepo;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public RestResponse findAll() {
        RestResponse restResponse = new RestResponse();

        try {
            List<Role> roles = roleRepo.findAll();
            List<ResRole> result = new ArrayList<>();

            for (Role r : roles) {
                ResRole resRole = modelMapper.map(r, ResRole.class);

                Set<ResPermission> permissions = new HashSet<>();

                for (Permission permission : r.getPermissions()) {
                    ResPermission resPermission = modelMapper.map(permission, ResPermission.class);

                    permissions.add(resPermission);
                }

                resRole.setResPermissions(permissions);

                result.add(resRole);
            }

            restResponse.setMessage("Find all user success");
            restResponse.setData(result);
            restResponse.setSuccess(true);
            restResponse.setStatusCode(HttpStatus.OK.value());
        } catch (Exception e) {
            restResponse.setMessage("Failed to get all user: " + e.getMessage());
            restResponse.setSuccess(false);
            restResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
        }

        return restResponse;
    }

    @Override
    public RestResponse findById(ReqRoleId reqRoleId) {
        RestResponse restResponse = new RestResponse();

        try {
            Role role = roleRepo.findById(reqRoleId.getId()).orElse(null);
            ResRole result = modelMapper.map(role, ResRole.class);

            Set<ResPermission> permissions = new HashSet<>();

            for (Permission permission : role.getPermissions()) {
                ResPermission resPermission = modelMapper.map(permission, ResPermission.class);

                permissions.add(resPermission);
            }

            result.setResPermissions(permissions);

            restResponse.setMessage("Find role success");
            restResponse.setData(result);
            restResponse.setSuccess(true);
            restResponse.setStatusCode(HttpStatus.OK.value());
        } catch (Exception e) {
            restResponse.setMessage("Failed to get role: " + e.getMessage());
            restResponse.setSuccess(false);
            restResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
        }

        return restResponse;
    }

    @Override
    public RestResponse createOrUpdate(ReqRole req) {
        RestResponse restResponse = new RestResponse();
        boolean update = false;

        try {
            if (req.getId() != null && roleRepo.existsById(req.getId())) {
                Role role = roleRepo.findById(req.getId()).orElse(null);
                req.setCreateAt(role.getCreateAt());
                req.setCreateBy(role.getCreateBy());
                req.setUpdateAt(LocalDateTime.now());
                req.setUpdateBy("admin@gmail.com");
                update = true;
            } else {
                req.setCreateAt(LocalDateTime.now());
                req.setCreateBy("admin@gmail.com");
            }

            Set<ReqPermissionId> permissionIds = req.getReqPermissionIds();
            Set<Permission> permissions = new HashSet<>();

            for (ReqPermissionId permissionId : permissionIds) {
                Permission permission = permissionRepo.findById(permissionId.getId()).orElse(null);
                if (permission != null)
                    permissions.add(permission);
            }

            Role role = modelMapper.map(req, Role.class);

            role.setPermissions(permissions);

            roleRepo.save(role);
            ResRole result = modelMapper.map(role, ResRole.class);

            Set<ResPermission> resPermissions = new HashSet<>();

            for (Permission permission : role.getPermissions()) {
                ResPermission resPermission = modelMapper.map(permission, ResPermission.class);

                resPermissions.add(resPermission);
            }

            result.setResPermissions(resPermissions);

            restResponse.setMessage(update ? "Update role success" : "Create role success");
            restResponse.setData(result);
            restResponse.setSuccess(true);
            restResponse.setStatusCode(HttpStatus.OK.value());
        } catch (Exception e) {
            restResponse.setMessage(update ? ("Failed to update role: " + e.getMessage())
                    : ("Failed to create role: " + e.getMessage()));
            restResponse.setSuccess(false);
            restResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
        }

        return restResponse;
    }

    @Override
    public RestResponse deleteById(ReqRoleId reqRoleId) {
        RestResponse restResponse = new RestResponse();

        try {
            roleRepo.deleteById(reqRoleId.getId());

            restResponse.setMessage("Delete role success");
            restResponse.setSuccess(true);
            restResponse.setStatusCode(HttpStatus.OK.value());
        } catch (Exception e) {
            restResponse.setMessage("Failed to delete role: " + e.getMessage());
            restResponse.setSuccess(false);
            restResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
        }

        return restResponse;
    }
}
