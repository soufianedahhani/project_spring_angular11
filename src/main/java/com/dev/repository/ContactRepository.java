package com.dev.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dev.entities.Contact;

public interface ContactRepository extends JpaRepository<Contact, Integer> {


	boolean existsByEmail(String email);

	boolean existsByIdAndEmail(Integer id, String email);
	
	
}
