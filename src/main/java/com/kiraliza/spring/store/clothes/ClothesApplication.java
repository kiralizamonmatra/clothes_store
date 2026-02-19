package com.kiraliza.spring.store.clothes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class ClothesApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClothesApplication.class, args);
	}

}
