package com.anand.entities;

import java.util.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
	
	@Id
	@Column(name = "productId")
	private String id;
	private String title;
	private String decscription;
	private int price;
	private int discountPrice;
	private int quantity;
	private Date addedDate;
	private boolean live;
	private boolean stock;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "category_Id")
	private Category category;
	
	

}
