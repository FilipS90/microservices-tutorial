package com.filips90.inventoryservice.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDtoIn {
	private List<OrderLineItemsDto> orderLineItemsDto;
}