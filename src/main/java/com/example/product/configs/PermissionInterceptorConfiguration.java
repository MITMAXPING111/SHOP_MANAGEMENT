package com.example.product.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class PermissionInterceptorConfiguration implements WebMvcConfigurer {

    @Bean
    public PermissionInterceptor permissionInterceptor() {
        return new PermissionInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(permissionInterceptor())
                .excludePathPatterns(
                        "/api/v1/auth/login",
                        "/api/v1/auth/register-user",
                        "/api/v1/auth/register-customer",
                        "/api/v1/auth/refresh",
                        "/api/v1/provinces/**",
                        "/login/oauth2/code/google",
                        "/api/v1/auth/oauth2/success",
                        "/api/v1/email/**");
    }
}
