package com.amit.practice.controller;

import com.amit.practice.dto.ProductSupplierDto;
import com.amit.practice.service.ProductSupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/** Composite key: productId + supplierId. No update (delete + create to change link). */
@RestController
@RequestMapping("/api/product-suppliers")
@RequiredArgsConstructor
public class ProductSupplierController {

    private final ProductSupplierService service;

    @GetMapping
    public List<ProductSupplierDto> findAll() {
        return service.findAll();
    }

    @GetMapping("/product/{productId}/supplier/{supplierId}")
    public ProductSupplierDto findByProductIdAndSupplierId(@PathVariable Long productId, @PathVariable Long supplierId) {
        return service.findByProductIdAndSupplierId(productId, supplierId);
    }

    @PostMapping
    public ResponseEntity<ProductSupplierDto> create(@RequestBody ProductSupplierDto dto) {
        ProductSupplierDto created = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @DeleteMapping("/product/{productId}/supplier/{supplierId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long productId, @PathVariable Long supplierId) {
        service.deleteByProductIdAndSupplierId(productId, supplierId);
    }
}
