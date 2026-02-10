package com.amit.practice.repository;

import com.amit.practice.model.ProductSupplier;
import com.amit.practice.model.ProductSupplierId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductSupplierRepository extends JpaRepository<ProductSupplier, ProductSupplierId> {
}
