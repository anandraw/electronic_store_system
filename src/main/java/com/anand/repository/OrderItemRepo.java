package com.anand.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anand.entities.OrderItem;

public interface OrderItemRepo extends JpaRepository<OrderItem, Integer>{

}
