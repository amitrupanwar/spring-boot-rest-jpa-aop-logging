package com.amit.practice.service;

import com.amit.practice.dto.ProductDto;
import com.amit.practice.model.Product;
import com.amit.practice.model.ProductCategory;
import com.amit.practice.repository.ProductCategoryRepository;
import com.amit.practice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;
    private final ProductCategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<ProductDto> findAll() {
        return repository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProductDto findById(Long id) {
        Product entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found: " + id));
        return toDto(entity);
    }

    @Transactional
    public ProductDto create(ProductDto dto) {
        ProductCategory category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("ProductCategory not found: " + dto.getCategoryId()));
        Product entity = Product.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .category(category)
                .build();
        entity = repository.save(entity);
        return toDto(entity);
    }

    @Transactional
    public ProductDto update(Long id, ProductDto dto) {
        Product entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found: " + id));
        if (dto.getCategoryId() != null) {
            ProductCategory category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("ProductCategory not found: " + dto.getCategoryId()));
            entity.setCategory(category);
        }
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity = repository.save(entity);
        return toDto(entity);
    }

    @Transactional
    public void deleteById(Long id) {
        if (!repository.existsById(id)) throw new RuntimeException("Product not found: " + id);
        repository.deleteById(id);
    }

    private ProductDto toDto(Product entity) {
        return ProductDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .price(entity.getPrice())
                .categoryId(entity.getCategory() != null ? entity.getCategory().getId() : null)
                .createdAt(entity.getCreatedAt())
                .createdBy(entity.getCreatedBy())
                .updatedAt(entity.getUpdatedAt())
                .updatedBy(entity.getUpdatedBy())
                .build();
    }
}
