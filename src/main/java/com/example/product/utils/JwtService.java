package com.example.product.utils;

import com.example.product.configs.CustomerDetailsImpl;
import com.example.product.configs.TokenBlacklistService;
import com.example.product.configs.UserDetailsImpl;
import com.example.product.entities.users.Customer;
import com.example.product.entities.users.User;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.Authentication;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JwtService {
    @Autowired
    private TokenBlacklistService tokenBlacklistService;

    @Value("${product.jwt.base64-secret}")
    private String SECRET_KEY;

    @Value("${product.jwt.access-token-validity-in-seconds}")
    private int timeExp;

    private final long refreshTokenExp = 7 * 24 * 60 * 60 * 1000L; // 7 ngày

    // Generate access token for User entity
    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("type", "USER");
        claims.put("permissions", user.getRoles().stream()
                .flatMap(r -> r.getPermissions().stream())
                .map(p -> p.getName())
                .collect(Collectors.toSet()));
        return createToken(claims, user.getEmail(), timeExp);
    }

    // Generate access token for Customer entity
    public String generateToken(Customer customer) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("type", "CUSTOMER");
        claims.put("customerId", customer.getId());
        return createToken(claims, customer.getEmail(), timeExp);
    }

    // New: Generate access token from UserDetails (support UserDetailsImpl,
    // CustomerDetailsImpl)
    public String generateToken(UserDetails userDetails) {
        if (userDetails instanceof UserDetailsImpl) {
            User user = ((UserDetailsImpl) userDetails).getUser();
            return generateToken(user);
        } else if (userDetails instanceof CustomerDetailsImpl) {
            Customer customer = ((CustomerDetailsImpl) userDetails).getCustomer();
            return generateToken(customer);
        } else {
            // Fallback: tạo token chỉ với username (email) không thêm claims
            return createToken(new HashMap<>(), userDetails.getUsername(), timeExp);
        }
    }

    // Generate refresh token for UserDetails (support UserDetailsImpl,
    // CustomerDetailsImpl)
    public String generateRefreshToken(UserDetails userDetails) {
        return createToken(new HashMap<>(), userDetails.getUsername(), refreshTokenExp);
    }

    // Tạo token dùng chung cho access hoặc refresh
    private String createToken(Map<String, Object> claims, String subject, long durationMs) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + durationMs))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public String extractEmail(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String extractUsername(String token) {
        return extractEmail(token);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        String email = extractEmail(token);
        boolean isSameUser = email.equals(userDetails.getUsername());
        boolean isNotExpired = !isTokenExpired(token);
        boolean isNotBlacklisted = !tokenBlacklistService.isBlacklisted(token);

        return isSameUser && isNotExpired && isNotBlacklisted;
    }

    private boolean isTokenExpired(String token) {
        Date expiration = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expiration.before(new Date());
    }

    public static Optional<String> getCurrentUserLogin() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(extractPrincipal(securityContext.getAuthentication()));
    }

    private static String extractPrincipal(Authentication authentication) {
        if (authentication == null) {
            return null;
        } else if (authentication.getPrincipal() instanceof UserDetails springSecurityUser) {
            return springSecurityUser.getUsername();
        } else if (authentication.getPrincipal() instanceof Jwt jwt) {
            return jwt.getSubject();
        } else if (authentication.getPrincipal() instanceof String s) {
            return s;
        }
        return null;
    }

    public String createTokenForOAuth2(String email) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("type", "OAUTH2_USER");
        return createToken(claims, email, timeExp);
    }
}
