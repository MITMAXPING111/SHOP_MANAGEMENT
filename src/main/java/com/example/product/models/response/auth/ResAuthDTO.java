package com.example.product.models.response.auth;

public class ResAuthDTO {
    private String token;
    private String message;

    public ResAuthDTO(String token) {
        this.token = token;
    }

    public ResAuthDTO(String token, String message) {
        this.token = token;
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public String getMessage() {
        return message;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
