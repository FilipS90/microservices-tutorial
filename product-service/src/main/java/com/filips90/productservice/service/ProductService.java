package com.filips90.productservice.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.filips90.productservice.dto.ProductRequest;
import com.filips90.productservice.dto.ProductResponse;
import com.filips90.productservice.dto.ProductMapperImpl;
import com.filips90.productservice.model.Product;
import com.filips90.productservice.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
	
	private final ProductRepository repository;
	private final ProductMapperImpl mapper;
	
	public void createProduct(ProductRequest dto) {
		Product product = Product.builder()
				.name(dto.getName())
				.description(dto.getDescription())
				.price(dto.getPrice())
				.build();
		repository.save(product);
		log.info("Product {} is saved", product.getId());
	}
	
	public List<ProductResponse> getAllProducts() {
		List<Product> allProducts = repository.findAll();
		return allProducts.stream()
				.map(e -> mapper.map(e))
				.collect(Collectors.toList());
	}
}
