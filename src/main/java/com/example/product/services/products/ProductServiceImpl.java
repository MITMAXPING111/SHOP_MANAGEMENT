package com.example.product.services.products;

import com.example.product.entities.managers.Supplier;
import com.example.product.entities.products.Category;
import com.example.product.entities.products.Product;
import com.example.product.models.request.products.ReqProductDTO;
import com.example.product.models.response.products.ResProductDTO;
import com.example.product.repositories.CategoryRepository;
import com.example.product.repositories.ProductRepository;
import com.example.product.repositories.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final SupplierRepository supplierRepository;

    @Override
    public ResProductDTO createProduct(ReqProductDTO req) {
        Product product = mapToEntity(req);
        product.setCreatedBy(req.getCreatedBy());
        product.setCreatedAt(LocalDateTime.now());
        productRepository.save(product);
        return mapToDTO(product);
    }

    @Override
    public ResProductDTO updateProduct(Long id, ReqProductDTO req) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

        product.setName(req.getName());
        product.setDescription(req.getDescription());
        product.setPrice(req.getPrice());
        product.setStockQuantity(req.getStockQuantity());
        product.setSku(req.getSku());
        product.setActive(req.getActive());
        product.setUpdatedAt(LocalDateTime.now());
        product.setUpdatedBy(req.getUpdatedBy());

        if (req.getCategoryId() != null) {
            Category category = categoryRepository.findById(req.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found with id: " + req.getCategoryId()));
            product.setCategory(category);
        }

        if (req.getSupplierId() != null) {
            Supplier supplier = supplierRepository.findById(req.getSupplierId())
                    .orElseThrow(() -> new RuntimeException("Supplier not found with id: " + req.getSupplierId()));
            product.setSupplier(supplier);
        }

        productRepository.save(product);
        return mapToDTO(product);
    }

    @Override
    public ResProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        return mapToDTO(product);
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        product.setActive(false); // Soft delete
        productRepository.save(product);
    }

    @Override
    public List<ResProductDTO> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Helper: Convert DTO to Entity
    private Product mapToEntity(ReqProductDTO req) {
        Product product = new Product();
        product.setName(req.getName());
        product.setDescription(req.getDescription());
        product.setPrice(req.getPrice());
        product.setStockQuantity(req.getStockQuantity());
        product.setSku(req.getSku());
        product.setActive(req.getActive());
        product.setCreatedAt(req.getCreatedAt());
        product.setCreatedBy(req.getCreatedBy());
        product.setUpdatedAt(req.getUpdatedAt());
        product.setUpdatedBy(req.getUpdatedBy());

        if (req.getCategoryId() != null) {
            Category category = categoryRepository.findById(req.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found with id: " + req.getCategoryId()));
            product.setCategory(category);
        }

        if (req.getSupplierId() != null) {
            Supplier supplier = supplierRepository.findById(req.getSupplierId())
                    .orElseThrow(() -> new RuntimeException("Supplier not found with id: " + req.getSupplierId()));
            product.setSupplier(supplier);
        }

        return product;
    }

    // Helper: Convert Entity to DTO
    private ResProductDTO mapToDTO(Product product) {
        return ResProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stockQuantity(product.getStockQuantity())
                .sku(product.getSku())
                .active(product.getActive())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .createdBy(product.getCreatedBy())
                .updatedBy(product.getUpdatedBy())

                // Chỉ lấy id và name của category nếu không có DTO riêng
                .category(product.getCategory() != null
                        ? Category.builder()
                                .id(product.getCategory().getId())
                                .name(product.getCategory().getName())
                                .build()
                        : null)

                // Chỉ lấy id và name của supplier nếu không có DTO riêng
                .supplier(product.getSupplier() != null
                        ? Supplier.builder()
                                .id(product.getSupplier().getId())
                                .name(product.getSupplier().getName())
                                .build()
                        : null)

                // KHÔNG LẤY productVariants và reviews để tránh vòng lặp
                .productVariants(null)
                .reviews(null)

                .build();
    }
}
