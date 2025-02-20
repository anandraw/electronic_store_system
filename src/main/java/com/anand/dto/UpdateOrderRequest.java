package com.anand.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data@NoArgsConstructor
@AllArgsConstructor
public class UpdateOrderRequest {
	
	private String orderStatus;
	private String paymentStatus;
	private String billingName;
	private String billingPhone;
	private String billingAddress;
	private Date devliveryDate;
	

}
