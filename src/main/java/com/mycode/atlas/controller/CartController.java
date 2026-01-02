package com.mycode.atlas.controller;

import java.math.BigDecimal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.DeleteExchange;

import com.mycode.atlas.exception.ResourceNotFound;
import com.mycode.atlas.model.Cart;
import com.mycode.atlas.response.ApiResponse;
import com.mycode.atlas.service.cart.ICartService;

import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/cart")
public class CartController {
	
	private final ICartService cartService;
//	private 
	  @GetMapping("/{cartId}/my-cart")
	  public ResponseEntity<ApiResponse> getUserCart(@PathVariable Long cartId)
	{
		try {
			Cart cart= cartService.getCart(cartId);
			return ResponseEntity.ok(new ApiResponse("successs", cart));
		} catch (ResourceNotFound e) {
			
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
		
	}
	@DeleteMapping("/{cartId}/clear")
	public ResponseEntity<ApiResponse> clearCart(@PathVariable  Long cartId)
	{
		try {
			cartService.clearCart(cartId);
			return ResponseEntity.ok(new ApiResponse("clear cart success", null));
		} catch (ResourceNotFound e) {
			// TODO Auto-generated catch block
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));

		}
		
		
	}
	
	@GetMapping("/{cartId}/cart/total-price")
	public ResponseEntity<ApiResponse> getTotalAmmount(@PathVariable  Long cartId)
	{
		try {
			BigDecimal totalPrice= cartService.getTotatlPrice(cartId);
			return ResponseEntity.ok(new ApiResponse("total price", totalPrice));
		} catch (ResourceNotFound e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
			
		}
	}
	

}
