package com.amit.practice.controller;

import com.amit.practice.dto.ProductTagDto;
import com.amit.practice.service.ProductTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/** Composite key: productId + tagId. No update (delete + create to change link). */
@RestController
@RequestMapping("/api/product-tags")
@RequiredArgsConstructor
public class ProductTagController {

    private final ProductTagService service;

    @GetMapping
    public List<ProductTagDto> findAll() {
        return service.findAll();
    }

    @GetMapping("/product/{productId}/tag/{tagId}")
    public ProductTagDto findByProductIdAndTagId(@PathVariable Long productId, @PathVariable Long tagId) {
        return service.findByProductIdAndTagId(productId, tagId);
    }

    @PostMapping
    public ResponseEntity<ProductTagDto> create(@RequestBody ProductTagDto dto) {
        ProductTagDto created = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @DeleteMapping("/product/{productId}/tag/{tagId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long productId, @PathVariable Long tagId) {
        service.deleteByProductIdAndTagId(productId, tagId);
    }
}
