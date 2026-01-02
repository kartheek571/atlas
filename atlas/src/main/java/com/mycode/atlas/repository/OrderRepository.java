package com.mycode.atlas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mycode.atlas.model.Order;

public interface OrderRepository  extends JpaRepository<Order, Long> {

	List<Order> findByUserId(Long userId);

}
