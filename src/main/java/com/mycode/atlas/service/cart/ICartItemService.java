package com.mycode.atlas.service.cart;

import java.math.BigDecimal;

import com.mycode.atlas.model.Cart;
import com.mycode.atlas.model.CartItem;

public interface ICartItemService {
	
	void addItemToCart(Long cartId , Long productId, int quantity);
	void removeItemFromCart(Long cartId , Long productId);

	   void updateItemQuantity(Long cartId, Long productId, int quantity);
	

	

}
