package com.example.product.configs;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();

        // Swagger config
        System.setProperty("SPRINGDOC_API_DOCS_PATH", dotenv.get("SPRINGDOC_API_DOCS_PATH"));
        System.setProperty("SPRINGDOC_SWAGGER_UI_PATH", dotenv.get("SPRINGDOC_SWAGGER_UI_PATH"));

        // Mail config
        System.setProperty("SPRING_MAIL_HOST", dotenv.get("SPRING_MAIL_HOST"));
        System.setProperty("SPRING_MAIL_PORT", dotenv.get("SPRING_MAIL_PORT"));
        System.setProperty("SPRING_MAIL_USERNAME", dotenv.get("SPRING_MAIL_USERNAME"));
        System.setProperty("SPRING_MAIL_PASSWORD", dotenv.get("SPRING_MAIL_PASSWORD"));
        System.setProperty("SPRING_MAIL_PROPERTIES_MAIL_SMTP_AUTH",
                dotenv.get("SPRING_MAIL_PROPERTIES_MAIL_SMTP_AUTH"));
        System.setProperty("SPRING_MAIL_PROPERTIES_MAIL_SMTP_STARTTLS_ENABLE",
                dotenv.get("SPRING_MAIL_PROPERTIES_MAIL_SMTP_STARTTLS_ENABLE"));

        // Paypal config
        System.setProperty("PAYPAL_CLIENTID", dotenv.get("PAYPAL_CLIENTID"));
        System.setProperty("PAYPAL_CLIENTSECRET", dotenv.get("PAYPAL_CLIENTSECRET"));
        System.setProperty("PAYPAL_MODE", dotenv.get("PAYPAL_MODE"));

        // JWT config
        System.setProperty("PRODUCT_JWT_BASE64_SECRET", dotenv.get("PRODUCT_JWT_BASE64_SECRET"));
        System.setProperty("PRODUCT_JWT_ACCESS_TOKEN_VALIDITY_IN_SECONDS",
                dotenv.get("PRODUCT_JWT_ACCESS_TOKEN_VALIDITY_IN_SECONDS"));
        System.setProperty("PRODUCT_JWT_REFRESH_TOKEN_VALIDITY_IN_SECONDS",
                dotenv.get("PRODUCT_JWT_REFRESH_TOKEN_VALIDITY_IN_SECONDS"));

        SpringApplication.run(Application.class, args);
    }
}
