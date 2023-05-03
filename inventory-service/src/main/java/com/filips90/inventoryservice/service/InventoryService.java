package com.filips90.inventoryservice.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.filips90.inventoryservice.dto.InventoryResponse;
import com.filips90.inventoryservice.model.Inventory;
import com.filips90.inventoryservice.repository.InventoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventoryService {
	
	private final InventoryRepository repository;
	
	@Transactional(readOnly = true)
	public List<InventoryResponse> isInStock(String[] skuCode) {
		return repository.findBySkuCodeIn(skuCode).stream()
				.map(i -> InventoryResponse.builder()
							.skuCode(i.getSkuCode())
							.isInStock(i.getQuantity() > 0)
							.build())
				.toList();
	}
	
	@Transactional
	public List<InventoryResponse> removeFromStock(String[] skuCode) {
		var inventories = repository.findBySkuCodeIn(skuCode);
		
		var changedInventories = inventories.stream()
			.map(Inventory::decrementQuantity).toList();
		
		changedInventories.forEach(e -> repository.save(e));
		
		return changedInventories.stream().map(i -> InventoryResponse.builder()
				.skuCode(i.getSkuCode())
				.isInStock(i.getQuantity() > 0)
				.build())
				.toList();
	}
}
