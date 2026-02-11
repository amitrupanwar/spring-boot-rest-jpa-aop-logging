package com.amit.practice.service;

import com.amit.practice.dto.InventoryDto;
import com.amit.practice.model.Inventory;
import com.amit.practice.model.Product;
import com.amit.practice.repository.InventoryRepository;
import com.amit.practice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository repository;
    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public List<InventoryDto> findAll() {
        return repository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public InventoryDto findByProductId(Long productId) {
        Inventory entity = repository.findByProduct_Id(productId)
                .orElseThrow(() -> new RuntimeException("Inventory not found for product: " + productId));
        return toDto(entity);
    }

    @Transactional
    public InventoryDto create(InventoryDto dto) {
        if (repository.findByProduct_Id(dto.getProductId()).isPresent()) {
            throw new RuntimeException("Inventory already exists for product: " + dto.getProductId());
        }
        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found: " + dto.getProductId()));
        Inventory entity = Inventory.builder()
                .product(product)
                .quantity(dto.getQuantity() != null ? dto.getQuantity() : 0)
                .build();
        entity = repository.save(entity);
        return toDto(entity);
    }

    @Transactional
    public InventoryDto update(Long productId, InventoryDto dto) {
        Inventory entity = repository.findByProduct_Id(productId)
                .orElseThrow(() -> new RuntimeException("Inventory not found for product: " + productId));
        entity.setQuantity(dto.getQuantity() != null ? dto.getQuantity() : entity.getQuantity());
        entity = repository.save(entity);
        return toDto(entity);
    }

    @Transactional
    public void deleteByProductId(Long productId) {
        repository.findByProduct_Id(productId)
                .ifPresentOrElse(repository::delete, () -> {
                    throw new RuntimeException("Inventory not found for product: " + productId);
                });
    }

    private InventoryDto toDto(Inventory entity) {
        return InventoryDto.builder()
                .productId(entity.getProduct().getId())
                .quantity(entity.getQuantity())
                .createdAt(entity.getCreatedAt())
                .createdBy(entity.getCreatedBy())
                .updatedAt(entity.getUpdatedAt())
                .updatedBy(entity.getUpdatedBy())
                .build();
    }
}
