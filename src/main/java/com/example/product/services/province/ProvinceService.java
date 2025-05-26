package com.example.product.services.province;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.product.models.request.province.ProvinceDTO;

import reactor.core.publisher.Mono;

import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProvinceService {
    @Autowired
    private WebClient webClient;

    private List<ProvinceDTO> provinces = new ArrayList<>();

    @PostConstruct
    public void init() {
        // Gọi API để lấy danh sách tỉnh/thành phố
        Mono<ProvinceDTO[]> provinceMono = webClient.get()
                .uri("https://provinces.open-api.vn/api/p/")
                .retrieve()
                .bodyToMono(ProvinceDTO[].class);

        provinces = List.of(provinceMono.block());
    }

    @Cacheable("provinces")
    public List<ProvinceDTO> getAllProvinces() {
        return provinces;
    }

    public List<ProvinceDTO> searchProvincesByName(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllProvinces();
        }
        String lowerKeyword = keyword.trim().toLowerCase();
        return provinces.stream()
                .filter(p -> p.getName().toLowerCase().contains(lowerKeyword))
                .collect(Collectors.toList());
    }

    public List<ProvinceDTO.DistrictDTO> getAllDistricts() {
        // Gọi API để lấy tất cả quận/huyện
        Mono<ProvinceDTO.DistrictDTO[]> districtMono = webClient.get()
                .uri("https://provinces.open-api.vn/api/d/")
                .retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                        response -> Mono
                                .error(new RuntimeException("Error fetching districts: " + response.statusCode())))
                .bodyToMono(ProvinceDTO.DistrictDTO[].class);

        return List.of(districtMono.block());
    }

    public List<ProvinceDTO.DistrictDTO> getDistrictsByProvinceId(String provinceId) {
        // Gọi API để lấy danh sách quận/huyện với depth=2
        Mono<ProvinceDTO> provinceMono = webClient.get()
                .uri("https://provinces.open-api.vn/api/p/{provinceId}?depth=2", provinceId)
                .retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                        response -> Mono
                                .error(new RuntimeException("Error fetching districts: " + response.statusCode())))
                .bodyToMono(ProvinceDTO.class);

        ProvinceDTO province = provinceMono.block();
        return province != null ? province.getDistricts() : new ArrayList<>();
    }

    public List<ProvinceDTO.DistrictDTO> searchDistrictsByName(String keyword, String provinceId) {
        if (keyword == null || keyword.trim().isEmpty()) {
            if (provinceId != null && !provinceId.trim().isEmpty()) {
                return getDistrictsByProvinceId(provinceId);
            }
            return getAllDistricts();
        }

        String lowerKeyword = keyword.trim().toLowerCase();
        if (provinceId != null && !provinceId.trim().isEmpty()) {
            // Tìm kiếm trong quận/huyện của tỉnh cụ thể
            List<ProvinceDTO.DistrictDTO> districts = getDistrictsByProvinceId(provinceId);
            return districts.stream()
                    .filter(d -> d.getName().toLowerCase().contains(lowerKeyword))
                    .collect(Collectors.toList());
        } else {
            // Tìm kiếm trong tất cả quận/huyện
            return getAllDistricts().stream()
                    .filter(d -> d.getName().toLowerCase().contains(lowerKeyword))
                    .collect(Collectors.toList());
        }
    }

    public List<ProvinceDTO.WardDTO> getAllWards() {
        // Gọi API để lấy tất cả phường/xã
        Mono<ProvinceDTO.WardDTO[]> wardMono = webClient.get()
                .uri("https://provinces.open-api.vn/api/w/")
                .retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                        response -> Mono.error(new RuntimeException("Error fetching wards: " + response.statusCode())))
                .bodyToMono(ProvinceDTO.WardDTO[].class);

        return List.of(wardMono.block());
    }

    public List<ProvinceDTO.WardDTO> getWardsByDistrictId(String districtId) {
        // Gọi API để lấy danh sách phường/xã với depth=2
        Mono<ProvinceDTO.DistrictDTO> districtMono = webClient.get()
                .uri("https://provinces.open-api.vn/api/d/{districtId}?depth=2", districtId)
                .retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                        response -> Mono.error(new RuntimeException("Error fetching wards: " + response.statusCode())))
                .bodyToMono(ProvinceDTO.DistrictDTO.class);

        ProvinceDTO.DistrictDTO district = districtMono.block();
        return district != null ? district.getWards() : new ArrayList<>();
    }

    public List<ProvinceDTO.WardDTO> searchWardsByName(String keyword, String districtId) {
        if (keyword == null || keyword.trim().isEmpty()) {
            if (districtId != null && !districtId.trim().isEmpty()) {
                return getWardsByDistrictId(districtId);
            }
            return getAllWards();
        }

        String lowerKeyword = keyword.trim().toLowerCase();
        if (districtId != null && !districtId.trim().isEmpty()) {
            // Tìm kiếm trong phường/xã của quận/huyện cụ thể
            List<ProvinceDTO.WardDTO> wards = getWardsByDistrictId(districtId);
            return wards.stream()
                    .filter(w -> w.getName().toLowerCase().contains(lowerKeyword))
                    .collect(Collectors.toList());
        } else {
            // Tìm kiếm trong tất cả phường/xã
            return getAllWards().stream()
                    .filter(w -> w.getName().toLowerCase().contains(lowerKeyword))
                    .collect(Collectors.toList());
        }
    }
}