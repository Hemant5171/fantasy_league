package com.hb.fantasyleague;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;

@SpringBootApplication
@EnableCircuitBreaker
public class FantasyLeagueApplication {

	public static void main(String[] args) {
		SpringApplication.run(FantasyLeagueApplication.class, args);
	}

}
