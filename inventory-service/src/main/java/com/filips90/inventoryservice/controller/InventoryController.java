package com.filips90.inventoryservice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.filips90.inventoryservice.dto.InventoryResponse;
import com.filips90.inventoryservice.dto.OrderDtoIn;
import com.filips90.inventoryservice.service.InventoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {
	
	private final InventoryService service;
	
	// example
	// http://localhost:8082/api/inventory?skuCode=iPhone-13&skuCode=iPhone13-red
	@PostMapping
	@ResponseStatus(HttpStatus.OK)
	public List<InventoryResponse> isInStock(@RequestBody OrderDtoIn order) {
		return service.isInStock(order);
	}
	
	@PostMapping("/stockDecrease")
	@ResponseStatus(HttpStatus.OK)
	public List<InventoryResponse> decreaseStock(@RequestBody OrderDtoIn order) {
		return service.decreaseStock(order);
	}
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<InventoryResponse> getStock() {
		return service.getStock();
	}
}
