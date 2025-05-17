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
public class ResCustomerDTO {
    private Long id;
    private String email;
    private String name;
    private boolean enabled;
    private String phone;
    private String avatar;
    private GenderEnum gender;

    private String createBy;
    private LocalDateTime createAt;
    private String updateBy;
    private LocalDateTime updateAt;

    private Set<ResRole> resRoles = new HashSet<>();
    private Set<Long> addressIds;
    private Long accountImageId;
}