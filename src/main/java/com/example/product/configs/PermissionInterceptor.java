package com.example.product.configs;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import com.example.product.entities.users.Customer;
import com.example.product.entities.users.Role;
import com.example.product.entities.users.User;
import com.example.product.services.users.UserService;
import com.example.product.services.customers.CustomerService;
import com.example.product.utils.JwtService;
import com.example.product.utils.error.PermissionException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class PermissionInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;

    @Autowired
    private CustomerService customerService;

    @Override
    @Transactional
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response, Object handler)
            throws Exception {

        // Lấy thông tin request
        String path = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        String requestURI = request.getRequestURI();
        String httpMethod = request.getMethod();

        // Log ra console
        System.out.println(">>> RUN preHandle");
        System.out.println(">>> path= " + path);
        System.out.println(">>> httpMethod= " + httpMethod);
        System.out.println(">>> requestURI= " + requestURI);

        // Lấy email User và Customer từ token JWT
        String userEmail = JwtService.getCurrentUserLogin().orElse(null);
        String customerEmail = JwtService.getCurrentUserLogin().orElse(null);

        if (userEmail != null && !userEmail.isEmpty()) {
            User user = userService.handleGetUserByUsername(userEmail);
            if (user != null) {
                if (!hasPermission(user.getRoles(), path, httpMethod)) {
                    throw new PermissionException("Bạn không có quyền truy cập endpoint này.");
                }
            } else {
                throw new PermissionException("User không tồn tại.");
            }
        } else if (customerEmail != null && !customerEmail.isEmpty()) {
            Customer customer = customerService.handleGetCustomerByUsername(customerEmail);
            if (customer != null) {
                if (!hasPermission(customer.getRoles(), path, httpMethod)) {
                    throw new PermissionException("Bạn không có quyền truy cập endpoint này.");
                }
            } else {
                throw new PermissionException("Customer không tồn tại.");
            }
        } else {
            // Không lấy được user hay customer
            throw new PermissionException("Bạn chưa đăng nhập hoặc không có quyền truy cập.");
        }

        return true;
    }

    private boolean hasPermission(Set<Role> roles, String path, String method) {
        if (roles == null || roles.isEmpty())
            return false;

        return roles.stream()
                .flatMap(role -> role.getPermissions().stream())
                .anyMatch(permission -> permission.getApiPath().equals(path)
                        && permission.getMethod().equals(method));
    }
}
