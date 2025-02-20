package com.anand.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.anand.dto.AddItemToCartReuqest;
import com.anand.dto.CartDTO;
import com.anand.entities.Cart;
import com.anand.entities.CartItem;
import com.anand.entities.Product;
import com.anand.entities.User;
import com.anand.exceptions.ResourceNotFoundException;
import com.anand.repository.CartItemRepo;
import com.anand.repository.CartRepo;
import com.anand.repository.ProductRepo;
import com.anand.repository.UserRepo;
import com.anand.service.CartService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CartServiceImpl implements CartService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private ProductRepo productRepo;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private CartRepo cartRepo;

	@Autowired
	private CartItemRepo cartItemRepo;

	@Override
	public CartDTO addItemsToCart(String userId, AddItemToCartReuqest addItemToCartReuqest) {

		 // Validate input
	    String productId = addItemToCartReuqest.getProductId();
	    int quantity = addItemToCartReuqest.getQuantity();
	    if (quantity <= 0) {
	        throw new ResourceNotFoundException("Requested quantity is not valid!");
	    }

	    // Fetch product and user
	    Product product = productRepo.findById(productId)
	            .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + productId));
	    User user = userRepo.findById(userId)
	            .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userId));

	    // Fetch or create cart
	    Cart cart = cartRepo.findByUser(user).orElseGet(() -> {
	        Cart newCart = new Cart();
	        newCart.setCartId(UUID.randomUUID().toString());
	        newCart.setCreatedDate(new Date());
	        newCart.setUser(user);
	        return cartRepo.save(newCart); // Save the new cart immediately
	    });

	    // Check if the product already exists in the cart
	    boolean isUpdated = false;
	    List<CartItem> cartItems = cart.getCartItems();
	    for (CartItem item : cartItems) {
	        if (item.getProduct().getId().equals(productId)) {
	            // Update existing item
	            item.setQuantity(quantity);
	            item.setTotalprice(quantity*product.getDiscountPrice());
	            isUpdated = true;
	            break;
	        }
	    }

	    // Add new item if not updated
	    if (!isUpdated) {
	        CartItem cartItem = new CartItem();
	        cartItem.setQuantity(quantity);
	        cartItem.setTotalprice(quantity*product.getDiscountPrice());
	        cartItem.setCart(cart);
	        cartItem.setProduct(product);
	        cartItems.add(cartItem);
	    }

	    // Save the updated cart
	    Cart updatedCart = cartRepo.save(cart);
	    return modelMapper.map(updatedCart, CartDTO.class);
		
	}

	@Override
	public void removeItemFromCart(String userId, int cartItems) {
		// TODO Auto-generated method stub

	}

	@Override
	public void clearCart(String userId) {
		// TODO Auto-generated method stub

	}

	@Override
	public CartDTO getCartByUser(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CartDTO createCart(CartDTO cartDTO, String userId) {
		 // Fetch user from repository
	    User user = userRepo.findById(userId)
	            .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userId));

	    // Create a new cart
	    Cart cart = new Cart();
	    cart.setCartId(UUID.randomUUID().toString());
	    cart.setCreatedDate(new Date());
	    cart.setUser(user);
	    
	    // Save the cart to the database
	    Cart savedCart = cartRepo.save(cart);

	    // Convert to DTO and return
	    return modelMapper.map(savedCart, CartDTO.class);
		  
	}

}
