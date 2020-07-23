package com.unicorntech.live.unicornapi.unicornapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableReactiveMongoRepositories
public class UnicornApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(UnicornApiApplication.class, args);
	}

}
