package com.anand.dto;

import java.util.Date;

import com.anand.entities.Category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
	
	private String id;
	private String title;
	private String decscription;
	private int price;
	private int discountPrice;
	private int quantity;
	private Date addedDate;
	private boolean live;
	private boolean stock;
	
	private Category category;

}
