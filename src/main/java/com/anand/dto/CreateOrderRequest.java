package com.anand.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreateOrderRequest {

	private String cartId;
	private String userId;
	private String orderStatus ="PENDING";
	private String paymentStatus = "NOTPAID";
	private String biilingAddress;
	private String billingPhone;
	private String billingName;
	
}
