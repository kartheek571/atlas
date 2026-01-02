package com.mycode.atlas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mycode.atlas.model.User;
@Repository
public interface UserRepository  extends JpaRepository<User, Long>{

	boolean existsByEmail(String email);

}
