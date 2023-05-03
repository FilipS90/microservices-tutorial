package com.filips90.productservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.filips90.productservice.model.Product;

public interface ProductRepository extends MongoRepository<Product, String> {}