package com.amit.practice.repository;

import com.amit.practice.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, com.amit.practice.model.Product> {

    Optional<Inventory> findByProduct_Id(Long productId);
}
