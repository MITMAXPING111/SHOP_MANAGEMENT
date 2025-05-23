package com.example.product.models.request.users;

import com.example.product.constants.GenderEnum;
import com.example.product.models.request.users.roles.ReqRoleId;

import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReqCustomerDTO {
    private String email;
    private String password;
    private String name;
    private boolean enabled;
    private String phone;
    private String avatar;
    private GenderEnum gender;

    private Set<ReqRoleId> reqRoleIds = new HashSet<>();
    private Set<Long> addressIds;
    private Long accountImageId;

    private String createdBy;
    private LocalDateTime createdAt;
    private String updatedBy;
    private LocalDateTime updatedAt;

    private AccountImageData accountImageData;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AccountImageData {
        private String url_image;
        private String id_image;
        private String id_folder;
    }
}
