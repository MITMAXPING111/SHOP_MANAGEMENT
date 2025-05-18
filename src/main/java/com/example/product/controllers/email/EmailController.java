package com.example.product.controllers.email;

import com.example.product.models.request.email.EmailRequest;
import com.example.product.services.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    // Gửi mã code tới email
    @PostMapping("/send-code")
    public ResponseEntity<String> sendCode(@RequestBody EmailRequest request) {
        emailService.sendHtmlEmailWithCode(request.getEmail());
        return ResponseEntity.ok("Đã gửi mã xác nhận đến email: " + request.getEmail());
    }

    // Xác thực mã code
    @PostMapping("/verify-code")
    public ResponseEntity<String> verifyCode(@RequestBody EmailRequest request) {
        boolean valid = emailService.validateCode(request.getEmail(), request.getCode());
        if (valid) {
            return ResponseEntity.ok("Mã xác nhận hợp lệ.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Mã xác nhận không hợp lệ hoặc đã hết hạn.");
        }
    }
}