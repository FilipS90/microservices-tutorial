package com.filips90.inventoryservice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.filips90.inventoryservice.dto.InventoryResponse;
import com.filips90.inventoryservice.service.InventoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {
	
	private final InventoryService service;
	
	// example
	// http://localhost:8082/api/inventory?skuCode=iPhone-13&skuCode=iPhone13-red
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<InventoryResponse> isInStock(@RequestParam String[] skuCode) {
		return service.isInStock(skuCode);
	}
	
	@PutMapping
	@ResponseStatus(HttpStatus.OK)
	public List<InventoryResponse> decrementItem(@RequestParam String[] skuCode) {
		return service.removeFromStock(skuCode);
	}
}
