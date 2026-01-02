package com.mycode.atlas.dto;

import java.math.BigDecimal;

import com.mycode.atlas.model.Product;

import lombok.Data;

@Data
public class CartItemDto {
	
	private Long itemId;
	private Integer quantity;
	private BigDecimal unitPrice;
	private ProductDto product;
	

}
