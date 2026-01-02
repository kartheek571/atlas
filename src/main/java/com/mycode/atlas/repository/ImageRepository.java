package com.mycode.atlas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mycode.atlas.model.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

}
