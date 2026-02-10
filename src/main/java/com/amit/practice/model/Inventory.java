package com.amit.practice.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Inventory {
    @Id
    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private Integer quantity;
}
