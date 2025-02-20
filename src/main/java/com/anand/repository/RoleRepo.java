package com.anand.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.anand.entities.Role;


public interface RoleRepo extends JpaRepository<Role, String> {

	Optional<Role> findByName(String name);
}
