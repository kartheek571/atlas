package com.mycode.atlas.service.order;

import java.util.List;

import com.mycode.atlas.dto.OrderDto;
import com.mycode.atlas.model.Order;

public interface IOrderService {
	
	Order placeOdrer(Long userId);
	OrderDto getOrder(Long userId);
	List<OrderDto> getUserOrders(Long userId);
	OrderDto convertToOrderDto(Order order);
	

}
