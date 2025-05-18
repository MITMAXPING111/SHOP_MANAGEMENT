package com.example.product.services.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    // Lưu mã + thời gian theo email
    private final Map<String, CodeData> codeStorage = new ConcurrentHashMap<>();

    // Gửi mã và lưu thời gian hiện tại
    public void sendHtmlEmailWithCode(String toEmail) {
        String code = String.format("%06d", new Random().nextInt(999999));
        Instant now = Instant.now();

        // Lưu mã + thời gian
        codeStorage.put(toEmail, new CodeData(code, now));

        String subject = "🔐 Mã xác nhận tài khoản của bạn";
        String content = "<h2>🔐 Mã xác nhận của bạn</h2>" +
                "<p>Xin chào,</p>" +
                "<p>Bạn vừa yêu cầu một mã xác nhận để truy cập hệ thống của chúng tôi.</p>" +
                "<p><strong style='font-size:20px;'>Mã xác nhận của bạn là: <span style='color:#2d89ef'>" + code
                + "</span></strong></p>" +
                "<p>Mã sẽ hết hạn sau 5 phút.</p>" +
                "<p>Trân trọng,<br>Đội ngũ hỗ trợ</p>";

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(content, true);
            helper.setFrom("youremail@gmail.com");

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Không thể gửi email: " + e.getMessage(), e);
        }
    }

    // Kiểm tra mã nhập vào
    public boolean validateCode(String email, String codeInput) {
        CodeData codeData = codeStorage.get(email);

        if (codeData == null) {
            return false; // chưa gửi mã
        }

        // Kiểm tra hết hạn (5 phút)
        Instant createdAt = codeData.getCreatedAt();
        Instant now = Instant.now();
        long minutesElapsed = Duration.between(createdAt, now).toMinutes();

        if (minutesElapsed >= 5) {
            codeStorage.remove(email); // Xóa mã đã hết hạn
            return false; // mã hết hạn
        }

        // So sánh mã
        return codeData.getCode().equals(codeInput);
    }
}
