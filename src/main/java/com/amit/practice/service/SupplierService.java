package com.amit.practice.service;

import com.amit.practice.dto.SupplierDto;
import com.amit.practice.model.Supplier;
import com.amit.practice.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SupplierService {

    private final SupplierRepository repository;

    @Transactional(readOnly = true)
    public List<SupplierDto> findAll() {
        return repository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public SupplierDto findById(Long id) {
        Supplier entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Supplier not found: " + id));
        return toDto(entity);
    }

    @Transactional
    public SupplierDto create(SupplierDto dto) {
        Supplier entity = Supplier.builder()
                .name(dto.getName())
                .contact(dto.getContact())
                .build();
        entity = repository.save(entity);
        return toDto(entity);
    }

    @Transactional
    public SupplierDto update(Long id, SupplierDto dto) {
        Supplier entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Supplier not found: " + id));
        entity.setName(dto.getName());
        entity.setContact(dto.getContact());
        entity = repository.save(entity);
        return toDto(entity);
    }

    @Transactional
    public void deleteById(Long id) {
        if (!repository.existsById(id)) throw new RuntimeException("Supplier not found: " + id);
        repository.deleteById(id);
    }

    private SupplierDto toDto(Supplier entity) {
        return SupplierDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .contact(entity.getContact())
                .createdAt(entity.getCreatedAt())
                .createdBy(entity.getCreatedBy())
                .updatedAt(entity.getUpdatedAt())
                .updatedBy(entity.getUpdatedBy())
                .build();
    }
}
