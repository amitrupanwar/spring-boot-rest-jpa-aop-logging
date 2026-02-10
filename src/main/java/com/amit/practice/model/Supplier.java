package com.amit.practice.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String contact;

    // Many-to-Many: Product
    @ManyToMany(mappedBy = "suppliers")
    private Set<Product> products;
}
