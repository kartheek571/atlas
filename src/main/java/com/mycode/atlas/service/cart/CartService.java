package com.mycode.atlas.service.cart;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com.mycode.atlas.exception.ResourceNotFound;
import com.mycode.atlas.model.Cart;
import com.mycode.atlas.model.CartItem;
import com.mycode.atlas.repository.CartItemRepository;
import com.mycode.atlas.repository.CartReposity;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class CartService  implements ICartService {
	
	
	private final CartReposity cartRepo;
	private final CartItemRepository cartItemRepo;
	private final AtomicLong cartIdGenerator= new AtomicLong(0);

	@Override
	public Cart getCart(long Id) {
		// TODO Auto-generated method stub
		
		
		Cart cart= cartRepo.findById(Id).orElseThrow(
				()-> new ResourceNotFound("the cart not found with give id "+Id)
				);
		
		BigDecimal totatlAmmount= cart.getTotatlAmmount();
		cart.setTotatlAmmount(totatlAmmount);
		return cartRepo.save(cart);
	}

	
	@Transactional
	@Override
	public void clearCart(long id) {
		Cart cart=getCart(id);
		cartItemRepo.deleteAllByCartId(id);
		cart.getItems().clear();
		cartRepo.deleteById(id);
		
		
	}

	@Override
	public BigDecimal getTotatlPrice(long id) {
	
		Cart cart = getCart(id);
		return cart.getTotatlAmmount();     //.getItems().stream().map(CartItem :: getTotatlPrice).reduce(BigDecimal.ONE.ZERO, BigDecimal::add);

	}
	
	public Long initializeCart()
	{
		Cart newCart= new Cart();
		Long newCartId= cartIdGenerator.incrementAndGet();
		newCart.setId(newCartId);
		return cartRepo.save(newCart).getId();
	}


	public Cart getCartByUserId(Long userId) {
		
		return cartRepo.findByUserId(userId);
	}
	
	
	

}
