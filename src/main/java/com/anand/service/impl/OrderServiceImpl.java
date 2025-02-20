package com.anand.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.anand.dto.CreateOrderRequest;
import com.anand.dto.OrderDTO;
import com.anand.dto.UpdateOrderRequest;
import com.anand.entities.Cart;
import com.anand.entities.CartItem;
import com.anand.entities.Order;
import com.anand.entities.OrderItem;
import com.anand.entities.User;
import com.anand.exceptions.ResourceNotFoundException;
import com.anand.repository.CartRepo;
import com.anand.repository.OrderRepo;
import com.anand.repository.UserRepo;
import com.anand.service.OrderService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

	@Autowired
	private CartRepo cartRepo;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private OrderRepo orderRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public OrderDTO createOrder(CreateOrderRequest orderRequest) {

		String userId = orderRequest.getUserId();
		String cartId = orderRequest.getCartId();

		User user = userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User is not found!!" + userId));

		Cart cart = cartRepo.findById(cartId)
				.orElseThrow(() -> new ResourceNotFoundException("Cart is not found!!" + cartId));

		List<CartItem> cartItems = cart.getCartItems();

		if (cartItems.size() == 0) {
			throw new ResourceNotFoundException("Items Not Present In Your Cart..");
		}

		Order order = new Order();
		order.setBillingName(orderRequest.getBillingName());
		order.setBillingPhone(orderRequest.getBillingPhone());
		order.setBillingAddress(orderRequest.getBiilingAddress());
		order.setOrderedDate(new Date());
		order.setDeliveryDate(null);
		order.setPaymentStatus(orderRequest.getPaymentStatus());
		order.setOrderStatus(orderRequest.getOrderStatus());
		order.setOrderId(UUID.randomUUID().toString());
		order.setUser(user);

		AtomicReference<Integer> orderAmount = new AtomicReference<>(0); 
		List<OrderItem> orderitems = cartItems.stream().map(cartItem -> {
			// Create a new OrderItem for each CartItem
			OrderItem orderItem = new OrderItem();
			orderItem.setQty(cartItem.getQuantity());
			orderItem.setProduct(cartItem.getProduct());
			
			// Calculate the total price for the OrderItem
			int totalPrice = cartItem.getQuantity() * cartItem.getProduct().getDiscountPrice();
			
			orderItem.setTotalPrice(totalPrice);
			// Set the order for the OrderItem
			orderItem.setOrder(order);
			
			// Update the total order amount
			orderAmount.set(orderAmount.get() + totalPrice);
			return orderItem;
		}).collect(Collectors.toList());

		order.setItems(orderitems);
		order.setOrderAmount(orderAmount.get());

		cart.getCartItems().clear();
		cartRepo.save(cart);
		Order saveOrder = orderRepo.save(order);
		return modelMapper.map(saveOrder, OrderDTO.class); 
	}

	@Override
	public void removeOrder(String orderid) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<OrderDTO> getOrdersOfUser(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OrderDTO getOrder(String orderId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OrderDTO updateOrder(String orderid, UpdateOrderRequest orderRequest) {
		// TODO Auto-generated method stub
		return null;
	}

}
