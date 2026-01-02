package com.mycode.atlas.service.order;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.mycode.atlas.dto.OrderDto;
import com.mycode.atlas.enums.OrderStatus;
import com.mycode.atlas.exception.ResourceNotFound;
import com.mycode.atlas.model.Cart;
import com.mycode.atlas.model.CartItem;
import com.mycode.atlas.model.Order;
import com.mycode.atlas.model.OrderItem;
import com.mycode.atlas.model.Product;
import com.mycode.atlas.repository.OrderRepository;
import com.mycode.atlas.repository.ProductRepository;
import com.mycode.atlas.service.cart.CartService;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class OrderService  implements IOrderService {
	
	
	
	private final OrderRepository orderRepo;
	private final ProductRepository productRepo;
	private final CartService  cartService;
	
	private final ModelMapper modelMapper;
	
	@Override
	public Order placeOdrer(Long userId) {
		Cart cart=cartService.getCartByUserId(userId);
		
		
		Order order = createOrder(cart);
		List<OrderItem> orderItemslist=createOrderItems(order, cart);
		order.setOrderItems(new HashSet<>(orderItemslist));
		order.setTotalAmount(caluculateTotalAmount(orderItemslist));
		Order savedOrder=orderRepo.save(order);
		cartService.clearCart(cart.getId());
		
		
		return savedOrder;
	}
	
	
	private Order createOrder(Cart cart)
	{
		Order order= new Order();
		
		order.setUser(cart.getUser());
		order.setOrderStatus(OrderStatus.PENDING);
		order.setOrderDate(LocalDate.now());
		return order;
	}
	

	private List<OrderItem> createOrderItems(Order order , Cart cart)
	{
		return cart.getItems().stream().map(cartItem->{
			Product product= cartItem.getProduct();
			product.setInventory(product.getInventory()-cartItem.getQuantity());
			productRepo.save(product);
			return new OrderItem(
					cartItem.getQuantity(),
					cartItem.getUnitPrice(),
					order ,
					product	);
		}).toList(); 
	}
	
	private BigDecimal caluculateTotalAmount(List<OrderItem> orderItems)
	{
		  return orderItems.stream().map(item->item.getPrice()
				  .multiply(new BigDecimal(item.getQuantity())))
				  .reduce(BigDecimal.ZERO, BigDecimal::add);
	}
	
	
	@Override
	public OrderDto getOrder(Long orderId) {
		// TODO Auto-generated method stub
//		return orderRepo.findById(orderId).orElseThrow(()->new ResourceNotFound("Order not found"));
		return   orderRepo.findById(orderId)
				.map(this::convertToOrderDto)
				.orElseThrow(()->new ResourceNotFound("Order not foun"));
	}
	
	
	
	
	  @Override
	    public List<OrderDto> getUserOrders(Long userId) {
	        List<Order> orders = orderRepo.findByUserId(userId);
	        return  orders.stream().map(this :: convertToOrderDto).toList();
//	        return null;
	    }
	  
	  
	  private  OrderDto  convertToOrderDto (Order order)
	  {
		  return modelMapper.map(order, OrderDto.class);
	  }
	  
	  
	  

}
