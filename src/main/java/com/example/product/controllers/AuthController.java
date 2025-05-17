package com.example.product.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import com.example.product.configs.CustomerDetailsImpl;
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

        @PostMapping("/login")
        public ResponseEntity<?> login(@RequestBody ReqAuthDTO request, HttpServletResponse response) {
                // Xác thực tài khoản
                authManager.authenticate(
                                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

                // Kiểm tra User
                Optional<User> userOpt = userRepo.findByEmail(request.getEmail());
                if (userOpt.isPresent()) {
                        User user = userOpt.get();
                        UserDetailsImpl userDetails = new UserDetailsImpl(user);

                        String jwt = jwtService.generateToken(userDetails);
                        String refreshToken = jwtService.generateRefreshToken(userDetails);

                        ResponseCookie cookie = ResponseCookie.from("refresh_token", refreshToken)
                                        .httpOnly(true)
                                        .path("/api/v1/auth/refresh")
                                        .maxAge(7 * 24 * 60 * 60) // 7 ngày
                                        .build();
                        response.addHeader("Set-Cookie", cookie.toString());

                        return ResponseEntity.ok(new ResAuthDTO(jwt, "Đăng nhập thành công dưới vai trò: User"));
                }

                // Kiểm tra Customer
                Optional<Customer> customerOpt = customerRepo.findByEmail(request.getEmail());
                if (customerOpt.isPresent()) {
                        Customer customer = customerOpt.get();
                        CustomerDetailsImpl customerDetails = new CustomerDetailsImpl(customer);

                        String jwt = jwtService.generateToken(customerDetails);
                        String refreshToken = jwtService.generateRefreshToken(customerDetails);

                        ResponseCookie cookie = ResponseCookie.from("refresh_token", refreshToken)
                                        .httpOnly(true)
                                        .path("/api/v1/auth/refresh")
                                        .maxAge(7 * 24 * 60 * 60) // 7 ngày
                                        .build();
                        response.addHeader("Set-Cookie", cookie.toString());

                        return ResponseEntity.ok(new ResAuthDTO(jwt, "Đăng nhập thành công dưới vai trò: Customer"));
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
                        UserDetailsImpl userDetails = new UserDetailsImpl(userOpt.get());
                        if (jwtService.isTokenValid(refreshToken, userDetails)) {
                                String newAccessToken = jwtService.generateToken(userDetails);
                                return ResponseEntity
                                                .ok(new ResAuthDTO(newAccessToken, "Làm mới token thành công (User)"));
                        }
                }

                Optional<Customer> customerOpt = customerRepo.findByEmail(email);
                if (customerOpt.isPresent()) {
                        CustomerDetailsImpl customerDetails = new CustomerDetailsImpl(customerOpt.get());
                        if (jwtService.isTokenValid(refreshToken, customerDetails)) {
                                String newAccessToken = jwtService.generateToken(customerDetails);
                                return ResponseEntity.ok(
                                                new ResAuthDTO(newAccessToken, "Làm mới token thành công (Customer)"));
                        }
                }

                return ResponseEntity.status(403).body("Refresh token không hợp lệ");
        }

        @PostMapping("/logout")
        public ResponseEntity<?> logout(HttpServletResponse response) {
                ResponseCookie cookie = ResponseCookie.from("refresh_token", "")
                                .httpOnly(true)
                                .path("/api/v1/auth/refresh")
                                .secure(true)
                                .maxAge(0) // xóa cookie ngay lập tức
                                .build();
                response.addHeader("Set-Cookie", cookie.toString());

                return ResponseEntity.ok("Đăng xuất thành công");
        }
}
