package com.amit.practice.service;

import com.amit.practice.dto.ProductSupplierDto;
import com.amit.practice.model.Product;
import com.amit.practice.model.ProductSupplier;
import com.amit.practice.model.ProductSupplierId;
import com.amit.practice.model.Supplier;
import com.amit.practice.repository.ProductRepository;
import com.amit.practice.repository.ProductSupplierRepository;
import com.amit.practice.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductSupplierService {

    private final ProductSupplierRepository repository;
    private final ProductRepository productRepository;
    private final SupplierRepository supplierRepository;

    @Transactional(readOnly = true)
    public List<ProductSupplierDto> findAll() {
        return repository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProductSupplierDto findByProductIdAndSupplierId(Long productId, Long supplierId) {
        ProductSupplierId id = new ProductSupplierId(productId, supplierId);
        ProductSupplier entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("ProductSupplier not found: productId=" + productId + ", supplierId=" + supplierId));
        return toDto(entity);
    }

    @Transactional
    public ProductSupplierDto create(ProductSupplierDto dto) {
        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found: " + dto.getProductId()));
        Supplier supplier = supplierRepository.findById(dto.getSupplierId())
                .orElseThrow(() -> new RuntimeException("Supplier not found: " + dto.getSupplierId()));
        ProductSupplierId id = new ProductSupplierId(dto.getProductId(), dto.getSupplierId());
        if (repository.existsById(id)) {
            throw new RuntimeException("ProductSupplier already exists: productId=" + dto.getProductId() + ", supplierId=" + dto.getSupplierId());
        }
        ProductSupplier entity = ProductSupplier.builder()
                .id(id)
                .product(product)
                .supplier(supplier)
                .build();
        entity = repository.save(entity);
        return toDto(entity);
    }

    @Transactional
    public void deleteByProductIdAndSupplierId(Long productId, Long supplierId) {
        ProductSupplierId id = new ProductSupplierId(productId, supplierId);
        if (!repository.existsById(id)) throw new RuntimeException("ProductSupplier not found: productId=" + productId + ", supplierId=" + supplierId);
        repository.deleteById(id);
    }

    private ProductSupplierDto toDto(ProductSupplier entity) {
        return ProductSupplierDto.builder()
                .productId(entity.getProduct().getId())
                .supplierId(entity.getSupplier().getId())
                .createdAt(entity.getCreatedAt())
                .createdBy(entity.getCreatedBy())
                .updatedAt(entity.getUpdatedAt())
                .updatedBy(entity.getUpdatedBy())
                .build();
    }
}
