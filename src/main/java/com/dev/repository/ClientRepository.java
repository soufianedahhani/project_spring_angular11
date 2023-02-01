package com.dev.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dev.entities.Client;


public interface ClientRepository extends JpaRepository<Client, Integer>{

}
