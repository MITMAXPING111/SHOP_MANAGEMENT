package com.example.product.models.request.users;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.example.product.constants.GenderEnum;
import com.example.product.models.request.users.roles.ReqRoleId;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReqUserDTO {
    private Long id;
    private String email;
    private String password;
    private String name;
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

    private Set<ReqRoleId> reqRoleIds = new HashSet<>();
}
