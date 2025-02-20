package com.anand.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.anand.entities.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product, String> {
	
	@Query("SELECT u FROM Product u WHERE u.title = :title")
	Optional<Product> findByProductTitle(@Param("title") String title);
}
