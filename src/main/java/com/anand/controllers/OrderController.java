package com.anand.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anand.dto.CreateOrderRequest;
import com.anand.dto.OrderDTO;
import com.anand.exceptions.ResourceNotFoundException;
import com.anand.service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

	@Autowired
	private OrderService orderService;

	// Endpoint to create an order
	@PostMapping("/create")
	public ResponseEntity<OrderDTO> createOrder(@RequestBody CreateOrderRequest orderRequest) {
		try {
			// Call the service method to create the order
			OrderDTO orderDTO = orderService.createOrder(orderRequest);
			return new ResponseEntity<>(orderDTO, HttpStatus.CREATED);
		} catch (ResourceNotFoundException e) {
			// Handle resource not found exception
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			// Handle other exceptions
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
