package com.mycode.atlas.dto;

import java.math.BigDecimal;
import java.util.Set;

import lombok.Data;

@Data
public class CartDto {
	
	
	private Long id;
	
	private BigDecimal totalAmount;
	private Set<CartItemDto> items;
	

}
