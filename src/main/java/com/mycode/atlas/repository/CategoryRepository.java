package com.mycode.atlas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mycode.atlas.model.Category;

@Repository
public interface CategoryRepository  extends JpaRepository<Category, Long> {

	Category findByName(String name);

	boolean existsByName(String name);

}
