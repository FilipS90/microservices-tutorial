package com.filips90.inventoryservice.mapper;

import org.mapstruct.Mapper;

import com.filips90.inventoryservice.dto.InventoryResponse;
import com.filips90.inventoryservice.model.Inventory;

@Mapper(componentModel = "spring")
public interface InventoryMapper {
	Inventory mapToInventory(InventoryResponse inventory);
	InventoryResponse mapToInventoryResponse(Inventory inventory);
}
