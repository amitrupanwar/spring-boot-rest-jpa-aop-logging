package com.amit.practice.controller;

import com.amit.practice.dto.ProductReviewDto;
import com.amit.practice.service.ProductReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product-reviews")
@RequiredArgsConstructor
public class ProductReviewController {

    private final ProductReviewService service;

    @GetMapping
    public List<ProductReviewDto> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ProductReviewDto findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public ResponseEntity<ProductReviewDto> create(@RequestBody ProductReviewDto dto) {
        ProductReviewDto created = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ProductReviewDto update(@PathVariable Long id, @RequestBody ProductReviewDto dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }
}
