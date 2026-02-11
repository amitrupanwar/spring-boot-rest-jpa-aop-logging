package com.amit.practice.service;

import com.amit.practice.dto.ProductImageDto;
import com.amit.practice.model.Product;
import com.amit.practice.model.ProductImage;
import com.amit.practice.repository.ProductImageRepository;
import com.amit.practice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductImageService {

    private final ProductImageRepository repository;
    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public List<ProductImageDto> findAll() {
        return repository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProductImageDto findById(Long id) {
        ProductImage entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("ProductImage not found: " + id));
        return toDto(entity);
    }

    @Transactional
    public ProductImageDto create(ProductImageDto dto) {
        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found: " + dto.getProductId()));
        ProductImage entity = ProductImage.builder()
                .url(dto.getUrl())
                .product(product)
                .build();
        entity = repository.save(entity);
        return toDto(entity);
    }

    @Transactional
    public ProductImageDto update(Long id, ProductImageDto dto) {
        ProductImage entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("ProductImage not found: " + id));
        entity.setUrl(dto.getUrl());
        if (dto.getProductId() != null) {
            Product product = productRepository.findById(dto.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found: " + dto.getProductId()));
            entity.setProduct(product);
        }
        entity = repository.save(entity);
        return toDto(entity);
    }

    @Transactional
    public void deleteById(Long id) {
        if (!repository.existsById(id)) throw new RuntimeException("ProductImage not found: " + id);
        repository.deleteById(id);
    }

    private ProductImageDto toDto(ProductImage entity) {
        return ProductImageDto.builder()
                .id(entity.getId())
                .productId(entity.getProduct() != null ? entity.getProduct().getId() : null)
                .url(entity.getUrl())
                .createdAt(entity.getCreatedAt())
                .createdBy(entity.getCreatedBy())
                .updatedAt(entity.getUpdatedAt())
                .updatedBy(entity.getUpdatedBy())
                .build();
    }
}
