package com.amit.practice.model;

import com.amit.practice.audit.Auditable;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "product_supplier")
public class ProductSupplier extends Auditable {
    @EmbeddedId
    private ProductSupplierId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("supplierId")
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;
}
