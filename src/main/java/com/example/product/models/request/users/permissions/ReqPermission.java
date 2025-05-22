package com.example.product.models.request.users.permissions;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

import com.example.product.constants.PermissionEnum;

@Getter
@Setter
public class ReqPermission {
    private Integer id;
    @Enumerated(EnumType.STRING)
    private PermissionEnum name;

    private String apiPath;
    private String method;
    private String module;

    // private LocalDateTime createdAt;
    // private LocalDateTime updatedAt;

}
