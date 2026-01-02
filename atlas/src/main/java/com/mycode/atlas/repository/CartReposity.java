package com.mycode.atlas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mycode.atlas.model.Cart;

@Repository
public interface CartReposity extends  JpaRepository<Cart, Long> {

	Cart findByUserId(Long userId);

}
