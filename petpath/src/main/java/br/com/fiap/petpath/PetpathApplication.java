package br.com.fiap.petpath;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class PetpathApplication {

	public static void main(String[] args) {
		SpringApplication.run(PetpathApplication.class, args);
	}

}
