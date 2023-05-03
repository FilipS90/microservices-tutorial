package com.filips90.inventoryservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.filips90.inventoryservice.model.Inventory;
import com.filips90.inventoryservice.repository.InventoryRepository;

@SpringBootApplication
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}
	
	@Bean
	CommandLineRunner loadDAta(InventoryRepository inventoryRepo) {
		return args -> {
			if(!inventoryRepo.findAll().isEmpty()) {
				return;
			}
			Inventory inventory = new Inventory();
			inventory.setSkuCode("iPhone_13");
			inventory.setQuantity(100);
			
			Inventory inventory1 = new Inventory();
			inventory1.setSkuCode("iPhone_13_red");
			inventory1.setQuantity(0);
			
			inventoryRepo.save(inventory);
			inventoryRepo.save(inventory1);
		};
	}

}
