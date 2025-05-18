package com.example.product.models.response.email;

public class EmailResponse {
    private String message;
    private String code;

    public EmailResponse() {
    }

    public EmailResponse(String message, String code) {
        this.message = message;
        this.code = code;
    }

    // Getters và Setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
