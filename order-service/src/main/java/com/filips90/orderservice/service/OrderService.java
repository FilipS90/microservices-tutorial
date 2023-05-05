package com.filips90.orderservice.service;

import java.util.List;
import java.util.UUID;

import org.springframework.http.MediaType;
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
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
	
	private final OrderRepository repository;
	private final WebClient.Builder webClientBuilder;
	
	
	public InventoryResponse[] placeOrder(OrderDtoIn dto) throws Exception {
		Order order = new Order();
		order.setOrderNumber(UUID.randomUUID().toString());
		
		List<OrderLineItems> items = dto.getOrderLineItemsDto()
			.stream()
			.map(this::map)
			.toList();
		order.setOrderLineItems(items);
		
		InventoryResponse[] inventoryStatus = checkItemsAvailability(dto);
		boolean allProductsinStock = 
				inventoryStatus.length == dto.getOrderLineItemsDto().size();
		
		if(!allProductsinStock) {
			throw new Exception("Not enough items on stock");
		}

		repository.save(order);
		return decrementItemInStock(dto);
	}
	
	private InventoryResponse[] checkItemsAvailability(OrderDtoIn dto) {
		return webClientBuilder.build().post()
				.uri("http://inventory-service/api/inventory")
				.contentType(MediaType.APPLICATION_JSON)
				.body(Mono.just(dto), OrderDtoIn.class)
				.retrieve()
				.bodyToMono(InventoryResponse[].class)
				.block();
	}
	
	private InventoryResponse[] decrementItemInStock(OrderDtoIn dto) {
		return webClientBuilder.build().post()
				.uri("http://inventory-service/api/inventory/stockDecrease")
				.contentType(MediaType.APPLICATION_JSON)
				.body(Mono.just(dto), OrderDtoIn.class)
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
