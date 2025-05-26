package com.example.product.controllers.province;

import com.example.product.models.request.province.ProvinceDTO;
import com.example.product.services.province.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/provinces")
public class ProvinceController {
    @Autowired
    private ProvinceService provinceService;

    @GetMapping
    public ResponseEntity<List<ProvinceDTO>> getAllProvinces() {
        return ResponseEntity.ok(provinceService.getAllProvinces());
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProvinceDTO>> searchProvinces(@RequestParam String keyword) {
        return ResponseEntity.ok(provinceService.searchProvincesByName(keyword));
    }

    @GetMapping("/districts")
    public ResponseEntity<List<ProvinceDTO.DistrictDTO>> getAllDistricts() {
        return ResponseEntity.ok(provinceService.getAllDistricts());
    }

    @GetMapping("/{provinceId}/districts")
    public ResponseEntity<List<ProvinceDTO.DistrictDTO>> getDistrictsByProvinceId(@PathVariable String provinceId) {
        return ResponseEntity.ok(provinceService.getDistrictsByProvinceId(provinceId));
    }

    @GetMapping("/districts/search")
    public ResponseEntity<List<ProvinceDTO.DistrictDTO>> searchDistricts(
            @RequestParam String keyword,
            @RequestParam(required = false) String provinceId) {
        return ResponseEntity.ok(provinceService.searchDistrictsByName(keyword, provinceId));
    }

    @GetMapping("/wards")
    public ResponseEntity<List<ProvinceDTO.WardDTO>> getAllWards() {
        return ResponseEntity.ok(provinceService.getAllWards());
    }

    @GetMapping("/districts/{districtId}/wards")
    public ResponseEntity<List<ProvinceDTO.WardDTO>> getWardsByDistrictId(@PathVariable String districtId) {
        return ResponseEntity.ok(provinceService.getWardsByDistrictId(districtId));
    }

    @GetMapping("/wards/search")
    public ResponseEntity<List<ProvinceDTO.WardDTO>> searchWards(
            @RequestParam String keyword,
            @RequestParam(required = false) String districtId) {
        return ResponseEntity.ok(provinceService.searchWardsByName(keyword, districtId));
    }
}
