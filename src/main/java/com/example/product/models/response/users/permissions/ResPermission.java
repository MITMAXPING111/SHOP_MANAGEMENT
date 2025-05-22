package com.example.product.models.response.users.permissions;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

import com.example.product.constants.PermissionEnum;

@Getter
@Setter
public class ResPermission {
    private Integer id;
    private PermissionEnum name;

    private String apiPath;
    private String method;
    private String module;

    private String createdBy;
    private LocalDateTime createdAt;
    private String updatedBy;
    private LocalDateTime updatedAt;
}
