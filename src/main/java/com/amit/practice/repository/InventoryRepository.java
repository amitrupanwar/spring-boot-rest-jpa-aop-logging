package com.amit.practice.repository;

import com.amit.practice.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, com.amit.practice.model.Product> {
}
