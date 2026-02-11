package com.amit.practice.service;

import com.amit.practice.dto.ProductTagDto;
import com.amit.practice.model.Product;
import com.amit.practice.model.ProductTag;
import com.amit.practice.model.ProductTagId;
import com.amit.practice.model.Tag;
import com.amit.practice.repository.ProductRepository;
import com.amit.practice.repository.ProductTagRepository;
import com.amit.practice.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductTagService {

    private final ProductTagRepository repository;
    private final ProductRepository productRepository;
    private final TagRepository tagRepository;

    @Transactional(readOnly = true)
    public List<ProductTagDto> findAll() {
        return repository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProductTagDto findByProductIdAndTagId(Long productId, Long tagId) {
        ProductTagId id = new ProductTagId(productId, tagId);
        ProductTag entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("ProductTag not found: productId=" + productId + ", tagId=" + tagId));
        return toDto(entity);
    }

    @Transactional
    public ProductTagDto create(ProductTagDto dto) {
        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found: " + dto.getProductId()));
        Tag tag = tagRepository.findById(dto.getTagId())
                .orElseThrow(() -> new RuntimeException("Tag not found: " + dto.getTagId()));
        ProductTagId id = new ProductTagId(dto.getProductId(), dto.getTagId());
        if (repository.existsById(id)) {
            throw new RuntimeException("ProductTag already exists: productId=" + dto.getProductId() + ", tagId=" + dto.getTagId());
        }
        ProductTag entity = ProductTag.builder()
                .id(id)
                .product(product)
                .tag(tag)
                .build();
        entity = repository.save(entity);
        return toDto(entity);
    }

    @Transactional
    public void deleteByProductIdAndTagId(Long productId, Long tagId) {
        ProductTagId id = new ProductTagId(productId, tagId);
        if (!repository.existsById(id)) throw new RuntimeException("ProductTag not found: productId=" + productId + ", tagId=" + tagId);
        repository.deleteById(id);
    }

    private ProductTagDto toDto(ProductTag entity) {
        return ProductTagDto.builder()
                .productId(entity.getProduct().getId())
                .tagId(entity.getTag().getId())
                .createdAt(entity.getCreatedAt())
                .createdBy(entity.getCreatedBy())
                .updatedAt(entity.getUpdatedAt())
                .updatedBy(entity.getUpdatedBy())
                .build();
    }
}
