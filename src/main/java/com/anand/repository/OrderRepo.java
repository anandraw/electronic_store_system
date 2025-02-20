package com.anand.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anand.entities.Order;

public interface OrderRepo extends JpaRepository<Order,String>{

}
