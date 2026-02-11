package com.amit.practice.service;

import com.amit.practice.dto.TagDto;
import com.amit.practice.model.Tag;
import com.amit.practice.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository repository;

    @Transactional(readOnly = true)
    public List<TagDto> findAll() {
        return repository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TagDto findById(Long id) {
        Tag entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tag not found: " + id));
        return toDto(entity);
    }

    @Transactional
    public TagDto create(TagDto dto) {
        Tag entity = Tag.builder().name(dto.getName()).build();
        entity = repository.save(entity);
        return toDto(entity);
    }

    @Transactional
    public TagDto update(Long id, TagDto dto) {
        Tag entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tag not found: " + id));
        entity.setName(dto.getName());
        entity = repository.save(entity);
        return toDto(entity);
    }

    @Transactional
    public void deleteById(Long id) {
        if (!repository.existsById(id)) throw new RuntimeException("Tag not found: " + id);
        repository.deleteById(id);
    }

    private TagDto toDto(Tag entity) {
        return TagDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .createdAt(entity.getCreatedAt())
                .createdBy(entity.getCreatedBy())
                .updatedAt(entity.getUpdatedAt())
                .updatedBy(entity.getUpdatedBy())
                .build();
    }
}
