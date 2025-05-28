package com.example.product.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.product.models.request.auth.AuthResponse;
import com.example.product.utils.CustomOAuth2UserService;

@RestController
@RequestMapping("/api/v1/auth")
public class OAuth2Controller {

    @Autowired
    private CustomOAuth2UserService oAuth2UserService;

    @GetMapping("/oauth2/success")
    public ResponseEntity<AuthResponse> oauth2LoginSuccess(@AuthenticationPrincipal OidcUser oidcUser) {
        String token = oAuth2UserService.processOAuth2User(oidcUser);
        return ResponseEntity.ok().body(new AuthResponse(token));
    }
}
