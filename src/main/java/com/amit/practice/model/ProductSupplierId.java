package com.amit.practice.model;

import jakarta.persistence.Embeddable;
import lombok.*;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductSupplierId implements Serializable {
    private Long productId;
    private Long supplierId;
}
