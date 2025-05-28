package com.example.product.utils;

import com.example.product.constants.RoleEnum;
import com.example.product.entities.users.Role;
import com.example.product.entities.users.User;
import com.example.product.repositories.RoleRepo;
import com.example.product.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomOAuth2UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepo roleRepository;

    @Autowired
    private JwtService jwtService;

    public String processOAuth2User(OidcUser oidcUser) {
        // Debug: Hiển thị toàn bộ attributes trả về từ provider
        System.out.println("OidcUser attributes: " + oidcUser.getAttributes());

        // Lấy email an toàn
        String email = oidcUser.getEmail();
        if (email == null || email.isBlank()) {
            // Thử lấy từ attributes nếu getEmail() trả về null
            Object rawEmail = oidcUser.getAttributes().get("email");
            if (rawEmail != null) {
                email = rawEmail.toString();
            }
        }

        if (email == null || email.isBlank()) {
            throw new RuntimeException("Email is null or not provided by OIDC provider.");
        }

        String name = oidcUser.getFullName();
        if (name == null || name.isBlank()) {
            name = oidcUser.getName(); // fallback
        }

        String avatar = oidcUser.getPicture();
        if (avatar == null || avatar.isBlank()) {
            Object pic = oidcUser.getAttributes().get("picture");
            avatar = pic != null ? pic.toString() : "";
        }

        System.out.println("Processing OAuth2 user with email: " + email);

        if (userRepository == null) {
            throw new RuntimeException("UserRepository is not injected properly.");
        }

        Optional<User> existingUser = userRepository.findByEmail(email);
        User user;
        if (existingUser.isPresent()) {
            user = existingUser.get();
        } else {
            user = new User();
            user.setEmail(email);
            user.setName(name);
            user.setAvatar(avatar);
            user.setEnabled(true);
            user.setPassword("");

            // Assign default role
            Optional<Role> role = roleRepository.findByName(RoleEnum.USER);
            if (role.isPresent()) {
                user.setRoles(new HashSet<>(Set.of(role.get())));
            } else {
                Role userRole = new Role();
                userRole.setName(RoleEnum.USER);
                roleRepository.save(userRole);
                user.setRoles(new HashSet<>(Set.of(userRole)));
            }

            user = userRepository.save(user);
        }

        // Generate JWT token
        String token = jwtService.generateToken(user);
        System.out.println("Generated JWT token: " + token);
        user.setAccessToken(token);
        userRepository.save(user);

        return token;
    }

    // New method to create UserDetails for authentication
    public UserDetails createUserDetails(User user) {
        Set<SimpleGrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName().name()))
                .collect(Collectors.toSet());
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword() != null ? user.getPassword() : "",
                user.isEnabled(), true, true, true, authorities);
    }
}