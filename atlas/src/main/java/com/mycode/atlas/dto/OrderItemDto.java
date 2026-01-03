package com.mycode.atlas.dto;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;



@Getter
@Data
@Setter
public class OrderItemDto {

	
	
	private long productId;
	private String productname;
	private String productBrand;
	
	private int quantity;
	private BigDecimal price;
	
}
