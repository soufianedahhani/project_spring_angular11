package com.dev.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dev.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	boolean existsByEmail(String email);

	boolean existsByUsername(String username);

	boolean existsByIdAndUsername(Integer id, String username);

	Optional<User> findOneByUsername(String username);

	

}
