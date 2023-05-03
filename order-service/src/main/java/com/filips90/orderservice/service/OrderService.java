package com.filips90.orderservice.service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import com.filips90.orderservice.dto.InventoryResponse;
import com.filips90.orderservice.dto.OrderDtoIn;
import com.filips90.orderservice.dto.OrderLineItemsDto;
import com.filips90.orderservice.model.Order;
import com.filips90.orderservice.model.OrderLineItems;
import com.filips90.orderservice.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
	
	private final OrderRepository repository;
	private final WebClient webClient;
	
	
	public InventoryResponse[] placeOrder(OrderDtoIn dto) throws Exception {
		Order order = new Order();
		order.setOrderNumber(UUID.randomUUID().toString());
		
		List<OrderLineItems> items = dto.getOrderLineItemsDto()
			.stream()
			.map(this::map)
			.toList();
		order.setOrderLineItems(items);
		
		List<String> skuCodes = order.getOrderLineItems()
			.stream()
			.map(OrderLineItems::getSkuCode)
			.toList();
		
		InventoryResponse[] itemsInventory = checkItemsAvailability(skuCodes);
		boolean allProductsinStock = 
				Arrays.stream(itemsInventory).allMatch(InventoryResponse::isInStock);
		
		if(!allProductsinStock) {
			throw new Exception("Not all items are in stock");
		}
		
		repository.save(order);
		
		return decrementItemInStock(skuCodes);
	}
	
	private InventoryResponse[] checkItemsAvailability(List<String> skuCodes) {
		return webClient.get()
				.uri("http://localhost:8082/api/inventory", 
						uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
				.retrieve()
				.bodyToMono(InventoryResponse[].class)
				.block();
	}
	
	private InventoryResponse[] decrementItemInStock(List<String> skuCodes) {
		return webClient.put()
				.uri("http://localhost:8082/api/inventory",
						uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
				.retrieve()
				.bodyToMono(InventoryResponse[].class)
				.block();
	}
	
	private OrderLineItems map(OrderLineItemsDto dto) {
		OrderLineItems items = new OrderLineItems();
		items.setPrice(dto.getPrice());
		items.setQuantity(dto.getQuantity());
		items.setSkuCode(dto.getSkuCode());
		return items;
	}
}
