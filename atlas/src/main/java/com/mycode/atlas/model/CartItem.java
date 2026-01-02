package com.mycode.atlas.model;

import java.math.BigDecimal;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CartItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="product_id")
	private Product   product ;
	private int quantity;
	private BigDecimal unitPrice;
	private BigDecimal totatlPrice;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="cart_id")
	private Cart cart;
	
	public void setTotalPrice()
	{
		this.totatlPrice=this.unitPrice.multiply(new BigDecimal(quantity));
	}
	
	

}
