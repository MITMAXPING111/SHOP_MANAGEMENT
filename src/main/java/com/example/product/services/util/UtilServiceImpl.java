package com.example.product.services.util;

import org.springframework.stereotype.Service;

import java.text.Normalizer;

@Service
public class UtilServiceImpl implements UtilService {
    @Override
    public String format(String input) {
        if (input == null || input.isBlank())
            return "";

        // B1: Chuyển Đ/đ riêng trước vì Normalizer không xử lý được
        input = input.replace("Đ", "D").replace("đ", "d");

        // B2: Loại bỏ dấu tiếng Việt
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        String noDiacritics = normalized.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");

        // B3: Thay các ký tự không phải chữ cái/số bằng dấu gạch ngang
        String replaced = noDiacritics.replaceAll("[^a-zA-Z0-9]", "-");

        // B4: Gộp các dấu gạch ngang liền nhau, bỏ đầu/cuối nếu có
        String cleaned = replaced.replaceAll("-+", "-").replaceAll("^-|-$", "");

        // B5: Chuyển thành chữ in hoa
        return cleaned.toUpperCase();
    }
}
