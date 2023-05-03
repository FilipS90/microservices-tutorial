package com.filips90.orderservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.filips90.orderservice.dto.InventoryResponse;
import com.filips90.orderservice.dto.OrderDtoIn;
import com.filips90.orderservice.service.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {
	
	private final OrderService service;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public InventoryResponse[] placeOrder(@RequestBody OrderDtoIn dto) throws Exception {
		return service.placeOrder(dto);
	}
}
