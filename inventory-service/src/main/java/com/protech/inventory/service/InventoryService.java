package com.protech.inventory.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.protech.inventory.dto.InventoryResponse;
import com.protech.inventory.repository.InventoryRepository;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {

	private final InventoryRepository inventoryRepository;

	@Transactional(readOnly = true)
	@SneakyThrows
	public List<InventoryResponse> isInStock(List<String> skuCode) {
		log.info("Wait started");
		Thread.sleep(8000);
		log.info("Wait ended");
		return inventoryRepository.findBySkuCodeIn(skuCode).stream().map(inventory -> InventoryResponse.builder()
				.skuCode(inventory.getSkuCode()).isInStock(inventory.getQuantity() > 0).build()).toList();
	}
}
