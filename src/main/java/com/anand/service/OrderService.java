package com.anand.service;

import java.util.List;
import com.anand.dto.CreateOrderRequest;
import com.anand.dto.OrderDTO;
import com.anand.dto.UpdateOrderRequest;

public interface OrderService {
	
	OrderDTO createOrder(CreateOrderRequest orderRequest);
	void removeOrder(String orderid);
	List<OrderDTO> getOrdersOfUser(String userId);
	OrderDTO getOrder(String orderId);
	OrderDTO updateOrder(String orderid,UpdateOrderRequest orderRequest);
	

}
