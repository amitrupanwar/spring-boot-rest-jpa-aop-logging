package com.amit.practice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/** Composite key: productId + supplierId. No separate id field. */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductSupplierDto {
    private Long productId;
    private Long supplierId;
    private Instant createdAt;
    private String createdBy;
    private Instant updatedAt;
    private String updatedBy;
}
