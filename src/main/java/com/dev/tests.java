package com.dev;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.dev.entities.Client;
import com.dev.repository.ClientRepository;
@Component
public class tests implements CommandLineRunner{

	@Autowired
	private ClientRepository clientRepository;
	
	public static void main(String[] args) {
	
	}

	@Override
	public void run(String... args) throws Exception {
		
		Client clt = new Client();
		clt.setNom("soufiane");
		clt.setPrenom("dahhani");
		clt.setEmail("soufianedahhani@gmail.com");
		clt.setAdresse("maroc");
		clt.setMatriculeFiscal("123");
		//clientRepository.save(clt);
	}

}
