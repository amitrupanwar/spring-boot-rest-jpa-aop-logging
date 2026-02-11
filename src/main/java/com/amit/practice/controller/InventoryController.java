package com.amit.practice.controller;

import com.amit.practice.dto.InventoryDto;
import com.amit.practice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/** Inventory is 1-to-1 with Product. Use productId in path for get/update/delete. */
@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService service;

    @GetMapping
    public List<InventoryDto> findAll() {
        return service.findAll();
    }

    @GetMapping("/by-product/{productId}")
    public InventoryDto findByProductId(@PathVariable Long productId) {
        return service.findByProductId(productId);
    }

    @PostMapping
    public ResponseEntity<InventoryDto> create(@RequestBody InventoryDto dto) {
        InventoryDto created = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/by-product/{productId}")
    public InventoryDto update(@PathVariable Long productId, @RequestBody InventoryDto dto) {
        return service.update(productId, dto);
    }

    @DeleteMapping("/by-product/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long productId) {
        service.deleteByProductId(productId);
    }
}
