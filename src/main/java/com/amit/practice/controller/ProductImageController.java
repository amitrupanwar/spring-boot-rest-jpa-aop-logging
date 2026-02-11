package com.amit.practice.controller;

import com.amit.practice.dto.ProductImageDto;
import com.amit.practice.service.ProductImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product-images")
@RequiredArgsConstructor
public class ProductImageController {

    private final ProductImageService service;

    @GetMapping
    public List<ProductImageDto> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ProductImageDto findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public ResponseEntity<ProductImageDto> create(@RequestBody ProductImageDto dto) {
        ProductImageDto created = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ProductImageDto update(@PathVariable Long id, @RequestBody ProductImageDto dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }
}
