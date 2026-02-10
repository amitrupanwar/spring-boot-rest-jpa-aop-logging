package com.amit.practice.repository;

import com.amit.practice.model.ProductTag;
import com.amit.practice.model.ProductTagId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductTagRepository extends JpaRepository<ProductTag, ProductTagId> {
}
