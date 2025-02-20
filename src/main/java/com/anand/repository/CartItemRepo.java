package com.anand.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anand.entities.CartItem;

public interface CartItemRepo extends JpaRepository<CartItem, Integer>{

}
