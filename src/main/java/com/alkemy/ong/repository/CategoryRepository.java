package com.alkemy.ong.repository;

import com.alkemy.ong.entity.Category;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {
	public Category findByName(String name);
	
	@Query("SELECT s from Category s ORDER BY s.timestamps ASC")
	public Page<Category> findAll(Pageable pageable);
	
}
