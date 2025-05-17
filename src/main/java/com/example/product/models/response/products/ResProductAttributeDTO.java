package com.example.product.models.response.products;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResProductAttributeDTO {
    private Long id;
    private String name;
    private List<ResProductAttributeValueDTO> productAttributeValues;

    private String createdBy;
    private LocalDateTime createdAt;
    private String updatedBy;
    private LocalDateTime updatedAt;
}