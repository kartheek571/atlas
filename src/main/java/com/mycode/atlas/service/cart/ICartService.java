package com.mycode.atlas.service.cart;

import java.math.BigDecimal;

import com.mycode.atlas.model.Cart;

public interface ICartService {
	
	Cart getCart(long Id);
	void clearCart(long id);
	BigDecimal getTotatlPrice(long id);
	Long initializeCart();
	
	Cart getCartByUserId(Long userId);
	

}
