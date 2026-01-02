package com.mycode.atlas.request;

import java.math.BigDecimal;

import com.mycode.atlas.model.Category;

import lombok.Data;

@Data
public class ProductUpdateRequest {

	
	private String name;
	private String brand;
	private BigDecimal price;
	private int inventory;
	private String description;
	
	
	private Category category;
	
	
}

