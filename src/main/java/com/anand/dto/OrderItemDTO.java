package com.anand.dto;

import com.anand.entities.Product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO {
	
	private int orderItemId;
	private int qty;
	private int totalPrice;
	private Product product;

}
