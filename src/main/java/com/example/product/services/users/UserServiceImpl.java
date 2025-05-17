package com.example.product.services.users;

import com.example.product.entities.users.Role;
import com.example.product.entities.users.User;
import com.example.product.models.request.users.ReqUserDTO;
import com.example.product.models.request.users.roles.ReqRoleId;
import com.example.product.models.response.users.ResUserDTO;
import com.example.product.models.response.users.permissions.ResPermission;
import com.example.product.models.response.users.roles.ResRole;
import com.example.product.repositories.RoleRepo;
import com.example.product.repositories.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepo roleRepo;

    @Override
    public ResUserDTO createUser(ReqUserDTO reqUserDTO) {
        User user = mapToEntity(reqUserDTO);
        user.setCreateAt(LocalDateTime.now());
        user.setUpdateAt(LocalDateTime.now());

        User savedUser = userRepository.save(user);
        return mapToDTO(savedUser);
    }

    @Override
    public ResUserDTO updateUser(Long userId, ReqUserDTO reqUserDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userId));

        BeanUtils.copyProperties(reqUserDTO, user, "id", "createAt", "createBy", "roles");
        user.setUpdateAt(LocalDateTime.now());

        Set<Role> roles = new HashSet<>();
        for (ReqRoleId roleId : reqUserDTO.getReqRoleIds()) {
            Role role = roleRepo.findById(roleId.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Role not found with ID: " + roleId.getId()));
            roles.add(role);
        }
        user.setRoles(roles);

        User updatedUser = userRepository.save(user);
        return mapToDTO(updatedUser);
    }

    @Override
    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException("User not found with ID: " + userId);
        }
        userRepository.deleteById(userId);
    }

    @Override
    public ResUserDTO getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userId));
        return mapToDTO(user);
    }

    @Override
    public List<ResUserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public User handleGetUserByUsername(String username) {
        return this.userRepository.findByEmail(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found with email: " + username));
    }

    public boolean isEmailExist(String email) {
        return this.userRepository.existsByEmail(email);
    }

    public void updateUserToken(String token, String email) {
        User currentUser = this.handleGetUserByUsername(email);
        if (currentUser != null) {
            currentUser.setRefreshToken(token);
            this.userRepository.save(currentUser);

        }
    }

    public User getUserByRefreshTokenAndEmail(String token, String email) {
        return this.userRepository.findByRefreshTokenAndEmail(token, email);
    }

    private User mapToEntity(ReqUserDTO dto) {
        User user = new User();
        BeanUtils.copyProperties(dto, user, "id", "reqRoleIds", "url_image");

        Set<Role> roles = new HashSet<>();
        for (ReqRoleId roleId : dto.getReqRoleIds()) {
            Role role = roleRepo.findById(roleId.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Role not found with ID: " + roleId.getId()));
            roles.add(role);
        }
        user.setRoles(roles);
        return user;
    }

    private ResUserDTO mapToDTO(User user) {
        Set<ResRole> resRoles = user.getRoles().stream()
                .map(role -> {
                    ResRole resRole = new ResRole();
                    resRole.setId(role.getId());
                    resRole.setName(role.getName());

                    // Mapping permissions
                    if (role.getPermissions() != null) {
                        Set<ResPermission> resPermissions = role.getPermissions().stream()
                                .map(permission -> {
                                    ResPermission resPermission = new ResPermission();
                                    resPermission.setId(permission.getId());
                                    resPermission.setName(permission.getName());
                                    return resPermission;
                                })
                                .collect(Collectors.toSet());
                        resRole.setResPermissions(resPermissions);
                    }

                    return resRole;
                })
                .collect(Collectors.toSet());

        return ResUserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .enabled(user.isEnabled())
                .phone(user.getPhone())
                .avatar(user.getAvatar())
                .province(user.getProvince())
                .ward(user.getWard())
                .addressDetail(user.getAddressDetail())
                .gender(user.getGender())
                .createdBy(user.getCreateBy())
                .createdAt(user.getCreateAt())
                .updatedBy(user.getUpdateBy())
                .updatedAt(user.getUpdateAt())
                .resRoles(resRoles)
                .url_image(user.getAccountImage() != null ? user.getAccountImage().getUrl_image() : null)
                .build();
    }

}