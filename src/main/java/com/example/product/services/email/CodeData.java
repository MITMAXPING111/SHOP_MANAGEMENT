package com.example.product.services.email;

import java.time.Instant;

public class CodeData {
    private String code;
    private Instant createdAt;

    public CodeData(String code, Instant createdAt) {
        this.code = code;
        this.createdAt = createdAt;
    }

    public String getCode() {
        return code;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
