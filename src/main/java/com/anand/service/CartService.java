package com.anand.service;

import com.anand.dto.AddItemToCartReuqest;
import com.anand.dto.CartDTO;

public interface CartService {
	
	CartDTO addItemsToCart(String userId, AddItemToCartReuqest addItemToCartReuqest);
	void removeItemFromCart(String userId,int cartItems);
	void clearCart(String userId);
	CartDTO getCartByUser(String userId);
	CartDTO createCart(CartDTO cartDTO, String userId);

}
