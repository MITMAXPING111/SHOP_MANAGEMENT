package com.example.product.services.products;

import com.example.product.entities.Category;
import com.example.product.entities.Product;
import com.example.product.entities.Supplier;
import com.example.product.models.request.ReqProductDTO;
import com.example.product.models.response.ResProductDTO;
import com.example.product.repositories.CategoryRepository;
import com.example.product.repositories.ProductRepository;
import com.example.product.repositories.SupplierRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Override
    public ResProductDTO createProduct(ReqProductDTO dto) {
        Product product = mapToEntity(dto);
        product.setCreatedAt(LocalDateTime.now());
        product.setCreatedBy(dto.getCreatedBy());
        Product saved = productRepository.save(product);
        return mapToResDTO(saved);
    }

    @Override
    public ResProductDTO updateProduct(Long id, ReqProductDTO dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + id));
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStockQuantity(dto.getStockQuantity());
        product.setUpdatedAt(LocalDateTime.now());
        product.setSku(dto.getSku());
        product.setUpdatedBy(dto.getUpdatedBy());

        return mapToResDTO(productRepository.save(product));
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public ResProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + id));
        return mapToResDTO(product);
    }

    @Override
    public List<ResProductDTO> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::mapToResDTO)
                .collect(Collectors.toList());
    }

    private Product mapToEntity(ReqProductDTO dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        // Có thể ánh xạ thêm category, supplier nếu cần
        // Ánh xạ category
        if (dto.getCategoryId() != null) {
            Category category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found with ID: " + dto.getCategoryId()));
            product.setCategory(category);
        }

        // Ánh xạ supplier
        if (dto.getSupplierId() != null) {
            Supplier supplier = supplierRepository.findById(dto.getSupplierId())
                    .orElseThrow(() -> new RuntimeException("Supplier not found with ID: " + dto.getSupplierId()));
            product.setSupplier(supplier);
        }
        return product;
    }

    private ResProductDTO mapToResDTO(Product product) {
        ResProductDTO dto = new ResProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setCreatedAt(product.getCreatedAt());
        dto.setUpdatedAt(product.getUpdatedAt());
        dto.setCreatedBy(product.getCreatedBy());
        dto.setUpdatedBy(product.getUpdatedBy());
        return dto;
    }
}
