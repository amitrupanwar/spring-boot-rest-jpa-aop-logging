package com.amit.practice.model;

import com.amit.practice.audit.Auditable;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Inventory extends Auditable {
    @Id
    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private Integer quantity;
}
