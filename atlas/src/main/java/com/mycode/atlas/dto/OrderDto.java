package com.mycode.atlas.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.mycode.atlas.enums.OrderStatus;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Data
public class OrderDto {
	
	
	private Long id;
	private Long userId;
	private LocalDate orderDate;
	private BigDecimal totalAmount;
	
	private OrderStatus orderStatus;
	private List<OrderItemDto> items;

}
