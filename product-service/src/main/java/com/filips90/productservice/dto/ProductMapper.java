package com.filips90.productservice.dto;

import org.mapstruct.Mapper;

import com.filips90.productservice.model.Product;

@Mapper
public interface ProductMapper {
	ProductResponse map(Product product);	
}
