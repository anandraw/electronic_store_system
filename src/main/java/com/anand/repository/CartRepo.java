package com.anand.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anand.entities.Cart;
import com.anand.entities.User;

public interface CartRepo extends JpaRepository<Cart, String> {
	 Optional<Cart> findByUser(User user);

}
