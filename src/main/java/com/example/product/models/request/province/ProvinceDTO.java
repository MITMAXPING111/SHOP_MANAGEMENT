package com.example.product.models.request.province;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProvinceDTO {
    @JsonProperty("code")
    private String id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("districts")
    private List<DistrictDTO> districts;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DistrictDTO {
        @JsonProperty("code")
        private String id;

        @JsonProperty("name")
        private String name;

        @JsonProperty("wards")
        private List<WardDTO> wards;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class WardDTO {
        @JsonProperty("code")
        private String id;

        @JsonProperty("name")
        private String name;
    }
}
