package com.example.product.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import com.example.product.entities.users.User;
import com.example.product.repositories.UserRepository;
import com.example.product.utils.CustomOAuth2UserService;
import com.example.product.utils.CustomUserDetailsService;
import com.example.product.utils.JwtFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

        @Autowired
        private JwtFilter jwtFilter;

        @Autowired
        private CustomUserDetailsService userDetailsService;

        @Autowired
        private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

        @Autowired
        @Qualifier("customOAuth2UserService")
        private CustomOAuth2UserService oAuth2UserService;

        @Autowired
        private UserRepository userRepository;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                return http
                                .csrf(csrf -> csrf.disable())
                                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers("/api/v1/auth/login",
                                                                "/api/v1/auth/register-user",
                                                                "/api/v1/auth/register-customer",
                                                                "/api/v1/auth/refresh",
                                                                "/api/v1/email/**",
                                                                "/api/v1/provinces/**",
                                                                "/v3/api-docs/**",
                                                                "/swagger-ui/**",
                                                                "/swagger-ui.html",
                                                                "/login/oauth2/code/google",
                                                                "/api/v1/auth/oauth2/success")
                                                .permitAll()
                                                .anyRequest().authenticated())
                                .exceptionHandling(ex -> ex.authenticationEntryPoint(customAuthenticationEntryPoint))
                                .oauth2Login(oauth2 -> oauth2
                                                .loginPage("/login")
                                                .userInfoEndpoint(userInfo -> userInfo
                                                                .userService(defaultOAuth2UserService())
                                                                .oidcUserService(customOidcUserService()))
                                                .successHandler((request, response, authentication) -> {
                                                        OidcUser oidcUser = (OidcUser) authentication.getPrincipal();
                                                        String token = oAuth2UserService.processOAuth2User(oidcUser);
                                                        User user = userRepository.findByEmail(oidcUser.getEmail())
                                                                        .orElseThrow(() -> new RuntimeException(
                                                                                        "User not found after OAuth2 login"));
                                                        UserDetails userDetails = oAuth2UserService
                                                                        .createUserDetails(user);
                                                        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                                                                        userDetails, null,
                                                                        userDetails.getAuthorities());
                                                        authToken.setDetails(new WebAuthenticationDetailsSource()
                                                                        .buildDetails(request));
                                                        SecurityContextHolder.getContext().setAuthentication(authToken);
                                                        response.sendRedirect(
                                                                        "/api/v1/auth/oauth2/success?token=" + token);
                                                })
                                                .failureHandler((request, response, exception) -> {
                                                        System.out.println("OAuth2 login failed: "
                                                                        + exception.getMessage());
                                                        response.sendRedirect("/auth/login?error");
                                                }))
                                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                                .build();
        }

        @Bean
        public OAuth2UserService<OAuth2UserRequest, OAuth2User> defaultOAuth2UserService() {
                return new DefaultOAuth2UserService();
        }

        @Bean
        public OAuth2UserService<OidcUserRequest, OidcUser> customOidcUserService() {
                OidcUserService oidcUserService = new OidcUserService();
                oidcUserService.setOauth2UserService(defaultOAuth2UserService());
                return oidcUserService;
        }

        @Bean
        public WebSecurityCustomizer webSecurityCustomizer() {
                return (web) -> web.ignoring().requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/swagger-resources/**",
                                "/webjars/**");
        }

        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
                return config.getAuthenticationManager();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }
}