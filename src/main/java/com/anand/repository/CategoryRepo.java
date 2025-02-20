package com.anand.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.anand.entities.Category;

public interface CategoryRepo extends JpaRepository<Category, String> {
	// Custom query to find a category by title containing the keyword
	// (case-insensitive)
	@Query("SELECT c FROM Category c WHERE LOWER(c.title) LIKE LOWER(concat('%', :keyword, '%'))")
	Optional<Category> findByTitleContainingIgnoreCase(@Param("keyword") String keyword);

}
