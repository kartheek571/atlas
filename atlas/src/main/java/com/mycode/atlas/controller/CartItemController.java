package com.mycode.atlas.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;

import com.mycode.atlas.exception.ResourceNotFound;
import com.mycode.atlas.model.Cart;
import com.mycode.atlas.model.User;
import com.mycode.atlas.response.ApiResponse;
import com.mycode.atlas.service.cart.ICartItemService;
import com.mycode.atlas.service.cart.ICartService;
import com.mycode.atlas.service.user.IUserService;

import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/cartItem")
public class CartItemController {
	
	private final ICartItemService cartICartItemService;
	private final ICartService cartService;
	private final IUserService userService;

	@PostMapping("/Item/add")
	public ResponseEntity<ApiResponse> addItemToCart( @RequestParam Long productId,  @RequestParam Integer quantity)
	{
		try {
			
			
			
			User user = userService.getAuthenticatedUser();
			Cart	cart=cartService.initializeCart(user);
			
			cartICartItemService.addItemToCart(cart.getId(), productId, quantity);
			return ResponseEntity.ok(new ApiResponse("add item  success", null));
		} catch (ResourceNotFound e) {
		
			
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
		catch (JwtException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse(e.getMessage(), null));
			// TODO: handle exception
		}
	}
	
	@DeleteMapping("/cart/{cartId}/item/{itemId}/remove")
	public ResponseEntity<ApiResponse> removeItemFromCart(@PathVariable Long cartId, @PathVariable Long productId)
	{
		try {
			cartICartItemService.removeItemFromCart(cartId, productId);
			return ResponseEntity.ok(new ApiResponse("Remove Item is successfull",null));
		} catch (ResourceNotFound e) {
			// TODO Auto-generated catch block
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}
	
	@PutMapping("/cart/{cartId}/item/{itemId}/update")
	public ResponseEntity<ApiResponse> updateItemQuaEntity(@PathVariable Long cartId, @PathVariable Long productId, @RequestParam int quantity)
	{
		try {
			cartICartItemService.updateItemQuantity(cartId, productId, quantity);
			return ResponseEntity.ok(new ApiResponse("update Item is success ", null));
		} catch (ResourceNotFound e) {
			// TODO Auto-generated catch block
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}

}
