package com.mycode.atlas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mycode.atlas.model.CartItem;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem , Long> {

	void deleteAllByCartId(long id);

}
