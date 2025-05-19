package com.example.product.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import com.example.product.configs.CustomerDetailsImpl;
import com.example.product.configs.TokenBlacklistService;
import com.example.product.configs.UserDetailsImpl;
import com.example.product.entities.users.Customer;
import com.example.product.entities.users.User;
import com.example.product.models.request.auth.ReqAuthDTO;
import com.example.product.models.response.auth.ResAuthDTO;
import com.example.product.repositories.CustomerRepository;
import com.example.product.repositories.UserRepository;
import com.example.product.utils.JwtService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

        @Autowired
        private AuthenticationManager authManager;

        @Autowired
        private JwtService jwtService;

        @Autowired
        private UserRepository userRepo;

        @Autowired
        private CustomerRepository customerRepo;

        @Autowired
        private TokenBlacklistService tokenBlacklistService;

        @PostMapping("/login")
        public ResponseEntity<?> login(@RequestBody ReqAuthDTO request, HttpServletResponse response) {
                // Xác thực tài khoản
                authManager.authenticate(
                                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

                // Xử lý User
                Optional<User> userOpt = userRepo.findByEmail(request.getEmail());
                if (userOpt.isPresent()) {
                        User user = userOpt.get();

                        // Blacklist token cũ nếu có
                        String oldRefreshToken = user.getRefreshToken();
                        String oldAccessToken = user.getAccessToken();
                        // Blacklist cả access token và refresh token cũ nếu tồn tại
                        if (oldAccessToken != null || oldRefreshToken != null) {
                                if (oldAccessToken != null) {
                                        tokenBlacklistService.blacklistToken(oldAccessToken);
                                }
                                if (oldRefreshToken != null) {
                                        tokenBlacklistService.blacklistToken(oldRefreshToken);
                                }
                        }

                        UserDetailsImpl userDetails = new UserDetailsImpl(user);
                        String newAccessToken = jwtService.generateToken(userDetails);
                        String newRefreshToken = jwtService.generateRefreshToken(userDetails);

                        // Cập nhật token mới
                        user.setAccessToken(newAccessToken);
                        user.setRefreshToken(newRefreshToken);
                        userRepo.save(user);

                        // Xóa cookie cũ và set cookie mới
                        ResponseCookie deleteCookie = ResponseCookie.from("refresh_token", "")
                                        .httpOnly(true)
                                        .path("/api/v1/auth/refresh")
                                        .secure(true)
                                        .maxAge(0)
                                        .build();
                        response.addHeader("Set-Cookie", deleteCookie.toString());

                        ResponseCookie cookie = ResponseCookie.from("refresh_token", newRefreshToken)
                                        .httpOnly(true)
                                        .path("/api/v1/auth/refresh")
                                        .secure(true)
                                        .maxAge(7 * 24 * 60 * 60)
                                        .build();
                        response.addHeader("Set-Cookie", cookie.toString());

                        return ResponseEntity
                                        .ok(new ResAuthDTO(newAccessToken, "Đăng nhập thành công dưới vai trò: User"));
                }

                // Xử lý Customer
                Optional<Customer> customerOpt = customerRepo.findByEmail(request.getEmail());
                if (customerOpt.isPresent()) {
                        Customer customer = customerOpt.get();

                        String oldRefreshToken = customer.getCurrentRefreshToken();
                        String oldAccessToken = customer.getCurrentAccessToken();
                        // Blacklist cả access token và refresh token cũ nếu tồn tại
                        if (oldAccessToken != null || oldRefreshToken != null) {
                                if (oldAccessToken != null) {
                                        tokenBlacklistService.blacklistToken(oldAccessToken);
                                }
                                if (oldRefreshToken != null) {
                                        tokenBlacklistService.blacklistToken(oldRefreshToken);
                                }
                        }

                        CustomerDetailsImpl customerDetails = new CustomerDetailsImpl(customer);
                        String newAccessToken = jwtService.generateToken(customerDetails);
                        String newRefreshToken = jwtService.generateRefreshToken(customerDetails);

                        customer.setCurrentAccessToken(newAccessToken);
                        customer.setCurrentRefreshToken(newRefreshToken);
                        customerRepo.save(customer);

                        ResponseCookie deleteCookie = ResponseCookie.from("refresh_token", "")
                                        .httpOnly(true)
                                        .path("/api/v1/auth/refresh")
                                        .secure(true)
                                        .maxAge(0)
                                        .build();
                        response.addHeader("Set-Cookie", deleteCookie.toString());

                        ResponseCookie cookie = ResponseCookie.from("refresh_token", newRefreshToken)
                                        .httpOnly(true)
                                        .path("/api/v1/auth/refresh")
                                        .secure(true)
                                        .maxAge(7 * 24 * 60 * 60)
                                        .build();
                        response.addHeader("Set-Cookie", cookie.toString());

                        return ResponseEntity.ok(
                                        new ResAuthDTO(newAccessToken, "Đăng nhập thành công dưới vai trò: Customer"));
                }

                return ResponseEntity.status(401).body("Email hoặc mật khẩu không đúng");
        }

        @PostMapping("/refresh")
        public ResponseEntity<?> refreshToken(HttpServletRequest request) {
                String refreshToken = null;
                if (request.getCookies() != null) {
                        for (var cookie : request.getCookies()) {
                                if (cookie.getName().equals("refresh_token")) {
                                        refreshToken = cookie.getValue();
                                        break;
                                }
                        }
                }

                if (refreshToken == null) {
                        return ResponseEntity.badRequest().body("Không tìm thấy refresh token");
                }

                String email = jwtService.extractUsername(refreshToken);
                if (email == null) {
                        return ResponseEntity.status(403).body("Refresh token không hợp lệ");
                }

                Optional<User> userOpt = userRepo.findByEmail(email);
                if (userOpt.isPresent()) {
                        User user = userOpt.get();
                        if (!refreshToken.equals(user.getRefreshToken()) ||
                                        tokenBlacklistService.isBlacklisted(refreshToken)) {
                                return ResponseEntity.status(403).body("Refresh token không hợp lệ");
                        }

                        UserDetailsImpl userDetails = new UserDetailsImpl(user);

                        // Blacklist access token cũ trước khi tạo cái mới
                        String oldAccessToken = user.getAccessToken();
                        if (oldAccessToken != null) {
                                tokenBlacklistService.blacklistToken(oldAccessToken);
                        }

                        String newAccessToken = jwtService.generateToken(userDetails);
                        user.setAccessToken(newAccessToken);
                        userRepo.save(user);
                        return ResponseEntity.ok(new ResAuthDTO(newAccessToken, "Làm mới token thành công (User)"));
                }

                Optional<Customer> customerOpt = customerRepo.findByEmail(email);
                if (customerOpt.isPresent()) {
                        Customer customer = customerOpt.get();
                        if (!refreshToken.equals(customer.getCurrentRefreshToken()) ||
                                        tokenBlacklistService.isBlacklisted(refreshToken)) {
                                return ResponseEntity.status(403).body("Refresh token không hợp lệ");
                        }

                        CustomerDetailsImpl customerDetails = new CustomerDetailsImpl(customer);

                        // Blacklist access token cũ
                        String oldAccessToken = customer.getCurrentAccessToken();
                        if (oldAccessToken != null) {
                                tokenBlacklistService.blacklistToken(oldAccessToken);
                        }

                        String newAccessToken = jwtService.generateToken(customerDetails);
                        customer.setCurrentAccessToken(newAccessToken);
                        customerRepo.save(customer);
                        return ResponseEntity.ok(new ResAuthDTO(newAccessToken, "Làm mới token thành công (Customer)"));
                }

                return ResponseEntity.status(403).body("Refresh token không hợp lệ");
        }

        @PostMapping("/logout")
        public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
                String accessToken = null;
                String refreshToken = null;

                // Lấy access token từ header Authorization
                String authHeader = request.getHeader("Authorization");
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                        accessToken = authHeader.substring(7);
                }

                // Lấy refresh token từ cookie
                if (request.getCookies() != null) {
                        for (var cookie : request.getCookies()) {
                                if ("refresh_token".equals(cookie.getName())) {
                                        refreshToken = cookie.getValue();
                                        break;
                                }
                        }
                }

                String role = "Không xác định";

                if (accessToken != null) {
                        String email = jwtService.extractUsername(accessToken);
                        if (email != null) {
                                // Kiểm tra là User hay Customer
                                Optional<User> userOpt = userRepo.findByEmail(email);
                                Optional<Customer> customerOpt = customerRepo.findByEmail(email);

                                if (userOpt.isPresent()) {
                                        User user = userOpt.get();
                                        if (accessToken.equals(user.getAccessToken())) {
                                                role = "User";
                                        }
                                        // Blacklist cả token đang lưu nếu khớp
                                        if (refreshToken != null && refreshToken.equals(user.getRefreshToken())) {
                                                tokenBlacklistService.blacklistToken(refreshToken);
                                        }
                                        if (accessToken.equals(user.getAccessToken())) {
                                                tokenBlacklistService.blacklistToken(accessToken);
                                        }
                                } else if (customerOpt.isPresent()) {
                                        Customer customer = customerOpt.get();
                                        if (accessToken.equals(customer.getCurrentAccessToken())) {
                                                role = "Customer";
                                        }
                                        if (refreshToken != null
                                                        && refreshToken.equals(customer.getCurrentRefreshToken())) {
                                                tokenBlacklistService.blacklistToken(refreshToken);
                                        }
                                        if (accessToken.equals(customer.getCurrentAccessToken())) {
                                                tokenBlacklistService.blacklistToken(accessToken);
                                        }
                                }
                        } else {
                                // Không trích xuất được username => token sai format hoặc bị lỗi
                                tokenBlacklistService.blacklistToken(accessToken);
                        }
                }

                // Nếu vẫn còn refreshToken (dù không match DB) thì blacklist
                if (refreshToken != null && !tokenBlacklistService.isBlacklisted(refreshToken)) {
                        tokenBlacklistService.blacklistToken(refreshToken);
                }

                // Xóa refresh_token cookie
                ResponseCookie deleteCookie = ResponseCookie.from("refresh_token", "")
                                .httpOnly(true)
                                .path("/api/v1/auth/refresh")
                                .secure(true)
                                .maxAge(0)
                                .build();
                response.addHeader("Set-Cookie", deleteCookie.toString());

                return ResponseEntity.ok("Đăng xuất thành công (" + role + ")");
        }
}
