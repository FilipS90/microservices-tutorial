package com.filips90.inventoryservice.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.filips90.inventoryservice.dto.InventoryResponse;
import com.filips90.inventoryservice.dto.OrderDtoIn;
import com.filips90.inventoryservice.mapper.InventoryMapper;
import com.filips90.inventoryservice.repository.InventoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventoryService {
	
	private final InventoryRepository repository;
	private final InventoryMapper inventoryMapper;
	
	@Transactional
	public List<InventoryResponse> isInStock(OrderDtoIn order) {
		// TODO REFACTOR THIS
		return repository.findAll()
				.stream()
				.filter(inventory -> order.getOrderLineItemsDto()
						.stream()
						.anyMatch(orderItem -> orderItem.getSkuCode().equals(inventory.getSkuCode()) 
								&& inventory.getQuantity() >= orderItem.getQuantity()))
				.map(e -> InventoryResponse.builder()
						.skuCode(e.getSkuCode())
						.quantity(e.getQuantity()).build())
				.toList();
	}
	
	@Transactional
	public List<InventoryResponse> decreaseStock(OrderDtoIn order) {
		return order.getOrderLineItemsDto()
				.stream()
				.map(e -> {
					var i = repository.findBySkuCode(e.getSkuCode());
					i.decrementQuantity(e.getQuantity());
					repository.save(i);
					var iResp = InventoryResponse.builder().skuCode(i.getSkuCode())
							.quantity(i.getQuantity()).build();
					return iResp;
				}).toList();
	}
	
	public List<InventoryResponse> getStock(){
		return repository.findAll().stream()
				.map(i -> inventoryMapper.mapToInventoryResponse(i))
				.toList();
	}
}
