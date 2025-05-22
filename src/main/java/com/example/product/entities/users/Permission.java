package com.example.product.entities.users;

import com.example.product.constants.PermissionEnum;
import com.example.product.utils.JwtService;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TBL_PERMISSIONS")
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private PermissionEnum name;

    @NotBlank(message = "apiPath không được để trống")
    private String apiPath;

    @NotBlank(message = "method không được để trống")
    private String method;

    @NotBlank(message = "module không được để trống")
    private String module;

    private String createdBy;
    private LocalDateTime createdAt;
    private String updatedBy;
    private LocalDateTime updatedAt;

    @ManyToMany(mappedBy = "permissions")
    private Set<Role> roles = new HashSet<>();

    public Permission(PermissionEnum name, String apiPath, String method, String module) {
        this.name = name;
        this.apiPath = apiPath;
        this.method = method;
        this.module = module;
    }

    @PrePersist
    public void handleBeforeCreate() {
        this.createdBy = JwtService.getCurrentUserLogin().isPresent() == true
                ? JwtService.getCurrentUserLogin().get()
                : "";

        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void handleBeforeUpdate() {
        this.updatedBy = JwtService.getCurrentUserLogin().isPresent() == true
                ? JwtService.getCurrentUserLogin().get()
                : "";

        this.updatedAt = LocalDateTime.now();
    }

}