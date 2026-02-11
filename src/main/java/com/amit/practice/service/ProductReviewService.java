package com.amit.practice.service;

import com.amit.practice.dto.ProductReviewDto;
import com.amit.practice.model.Product;
import com.amit.practice.model.ProductReview;
import com.amit.practice.repository.ProductRepository;
import com.amit.practice.repository.ProductReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductReviewService {

    private final ProductReviewRepository repository;
    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public List<ProductReviewDto> findAll() {
        return repository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProductReviewDto findById(Long id) {
        ProductReview entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("ProductReview not found: " + id));
        return toDto(entity);
    }

    @Transactional
    public ProductReviewDto create(ProductReviewDto dto) {
        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found: " + dto.getProductId()));
        ProductReview entity = ProductReview.builder()
                .reviewer(dto.getReviewer())
                .rating(dto.getRating())
                .comment(dto.getComment())
                .product(product)
                .build();
        entity = repository.save(entity);
        return toDto(entity);
    }

    @Transactional
    public ProductReviewDto update(Long id, ProductReviewDto dto) {
        ProductReview entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("ProductReview not found: " + id));
        entity.setReviewer(dto.getReviewer());
        entity.setRating(dto.getRating());
        entity.setComment(dto.getComment());
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
        if (!repository.existsById(id)) throw new RuntimeException("ProductReview not found: " + id);
        repository.deleteById(id);
    }

    private ProductReviewDto toDto(ProductReview entity) {
        return ProductReviewDto.builder()
                .id(entity.getId())
                .productId(entity.getProduct() != null ? entity.getProduct().getId() : null)
                .reviewer(entity.getReviewer())
                .rating(entity.getRating())
                .comment(entity.getComment())
                .createdAt(entity.getCreatedAt())
                .createdBy(entity.getCreatedBy())
                .updatedAt(entity.getUpdatedAt())
                .updatedBy(entity.getUpdatedBy())
                .build();
    }
}
