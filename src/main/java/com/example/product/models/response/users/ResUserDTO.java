package com.example.product.models.response.users;

import com.example.product.constants.GenderEnum;
import com.example.product.models.response.users.roles.ResRole;

import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResUserDTO {
    private Long id;
    private String email;
    private String name;
    private boolean enabled;
    private String phone;
    private String avatar;
    private String province;
    private String ward;
    private String addressDetail;
    private GenderEnum gender;

    private String createdBy;
    private LocalDateTime createdAt;
    private String updatedBy;
    private LocalDateTime updatedAt;

    private Set<ResRole> resRoles = new HashSet<>();
    private String url_image;
}
