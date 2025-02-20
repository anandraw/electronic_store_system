package com.anand.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.anand.entities.CartItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {
	
	private String cartId;
	private Date createdDate;
	private List<CartItem> cartItems = new ArrayList<>(); 

}
