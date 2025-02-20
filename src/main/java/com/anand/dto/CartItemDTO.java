package com.anand.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDTO {

	private int cartItemId;
	private ProductDTO product;
	private int quantity;
	private int totalprice;

}
