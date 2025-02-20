package com.anand.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.anand.entities.User;

@Repository
public interface UserRepo  extends JpaRepository<User, String>{
	
	@Query("SELECT u FROM User u WHERE u.email = :email")
	Optional<User> findByUserEmail(@Param("email") String email);


}
