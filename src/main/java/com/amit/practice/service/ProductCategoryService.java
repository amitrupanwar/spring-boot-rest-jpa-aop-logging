package com.amit.practice.service;

import com.amit.practice.dto.ProductCategoryDto;
import com.amit.practice.model.ProductCategory;
import com.amit.practice.repository.ProductCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductCategoryService {

    private final ProductCategoryRepository repository;

    @Transactional(readOnly = true)
    public List<ProductCategoryDto> findAll() {
        return repository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProductCategoryDto findById(Long id) {
        ProductCategory entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("ProductCategory not found: " + id));
        return toDto(entity);
    }

    @Transactional
    public ProductCategoryDto create(ProductCategoryDto dto) {
        ProductCategory entity = ProductCategory.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .build();
        entity = repository.save(entity);
        return toDto(entity);
    }

    @Transactional
    public ProductCategoryDto update(Long id, ProductCategoryDto dto) {
        ProductCategory entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("ProductCategory not found: " + id));
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity = repository.save(entity);
        return toDto(entity);
    }

    @Transactional
    public void deleteById(Long id) {
        if (!repository.existsById(id)) throw new RuntimeException("ProductCategory not found: " + id);
        repository.deleteById(id);
    }

    private ProductCategoryDto toDto(ProductCategory entity) {
        return ProductCategoryDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .createdAt(entity.getCreatedAt())
                .createdBy(entity.getCreatedBy())
                .updatedAt(entity.getUpdatedAt())
                .updatedBy(entity.getUpdatedBy())
                .build();
    }
}
