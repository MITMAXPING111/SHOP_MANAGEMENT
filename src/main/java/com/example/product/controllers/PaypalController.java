package com.example.product.controllers;

import com.example.product.services.paypal.PaypalService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/paypal")
public class PaypalController {

    private final PaypalService paypalService;

    public PaypalController(PaypalService paypalService) {
        this.paypalService = paypalService;
    }

    @PostMapping("/pay")
    public ResponseEntity<?> pay(@RequestParam("value") double value) {
        try {
            Payment payment = paypalService.createPayment(
                    // 1000.00,
                    value,
                    "USD",
                    "paypal",
                    "sale",
                    "Payment description",
                    "http://localhost:8080/api/paypal/cancel",
                    "http://localhost:8080/api/paypal/success");

            for (Links link : payment.getLinks()) {
                if (link.getRel().equals("approval_url")) {
                    // trả về URL để frontend redirect user đến PayPal thanh toán
                    return ResponseEntity.ok(link.getHref());
                }
            }
            return ResponseEntity.badRequest().body("Không tìm thấy link thanh toán");
        } catch (PayPalRESTException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Lỗi tạo thanh toán");
        }
    }

    @GetMapping("/success")
    public ResponseEntity<?> successPayment(@RequestParam("paymentId") String paymentId,
            @RequestParam("PayerID") String payerId) {
        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);

            if ("approved".equalsIgnoreCase(payment.getState())) {
                return ResponseEntity.ok("Tiêu dần cho quen nhá BTD");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Payment not approved.");
            }

        } catch (PayPalRESTException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred: " + e.getMessage());
        }
    }

    @GetMapping("/cancel")
    public ResponseEntity<String> cancel() {
        return ResponseEntity.ok("Thanh toán bị hủy");
    }
}
