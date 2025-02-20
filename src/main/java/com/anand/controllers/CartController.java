package com.anand.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anand.dto.AddItemToCartReuqest;
import com.anand.dto.CartDTO;
import com.anand.exceptions.ResourceNotFoundException;
import com.anand.service.CartService;

@RestController
@RequestMapping("/api/cart")
public class CartController {

	@Autowired
	private CartService cartService;

	// Endpoint to add items to the cart
	@PostMapping("/{userId}/items")
	public ResponseEntity<CartDTO> addItemsToCart(@PathVariable String userId,
			@RequestBody AddItemToCartReuqest addItemToCartRequest) {
		try {
			// Call the service method to add items to the cart
			CartDTO cartDTO = cartService.addItemsToCart(userId, addItemToCartRequest);
			return new ResponseEntity<>(cartDTO, HttpStatus.OK);
		} catch (ResourceNotFoundException e) {
			// Handle resource not found exception
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			// Handle other exceptions
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
	}
	
	  // Create a new cart
    @PostMapping("/{userId}/create")
    public ResponseEntity<CartDTO> createCart(
            @RequestBody CartDTO cartDTO, 
            @PathVariable String userId) {
        CartDTO newCart = cartService.createCart(cartDTO, userId);
        return ResponseEntity.ok(newCart);
    }

}
