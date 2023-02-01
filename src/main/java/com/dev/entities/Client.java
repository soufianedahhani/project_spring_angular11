package com.dev.entities;

import javax.persistence.Entity;

import lombok.Data;

@Data
@Entity
public class Client extends Contact{
	
	//private Integer id;
	private String matriculeFiscal;
	private String adresse;

}
