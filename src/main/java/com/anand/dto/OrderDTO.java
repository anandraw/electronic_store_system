package com.anand.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

	private String orderId;
	private String orderStatus = "PNEDING";
	private String paymentStatus = "NOTPAID";
	private int orderAmount;
	private String billingAddress;
	private String billingPhone;
	private String billingName;
	private Date orderedDate = new Date();
	private Date deliveryDate;
	
	
	private UserDTO user;

	private List<OrderItemDTO> orderItems = new ArrayList<>();

}
