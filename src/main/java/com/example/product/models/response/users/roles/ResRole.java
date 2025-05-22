package com.example.product.models.response.users.roles;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.example.product.constants.RoleEnum;
import com.example.product.models.response.users.permissions.ResPermission;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResRole {
    private Long id;
    private RoleEnum name;
    private String createdBy;
    private LocalDateTime createdAt;
    private String updatedBy;
    private LocalDateTime updatedAt;
    private Set<ResPermission> resPermissions = new HashSet<>();
}
