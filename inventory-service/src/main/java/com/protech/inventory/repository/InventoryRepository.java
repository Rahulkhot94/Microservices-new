package com.protech.inventory.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.protech.inventory.model.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory, Long>  {

//	boolean existsBySkuCodeAndQuantityIsGreaterThanEqual(String skuCode, int quantity);

	List<Inventory> findBySkuCodeIn(List<String> skuCode);
}
