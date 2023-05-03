package com.filips90.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.filips90.orderservice.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {}
