package com.mycode.atlas.dto;

import java.util.List;

import com.mycode.atlas.model.Cart;

import lombok.Data;

@Data
public class UserDto {
	
	
	private Long id;
	private String firstname;
	private String lastname;
	private String email;
	
	private List<OrderDto> orders;
	private CartDto cart;
	

}
