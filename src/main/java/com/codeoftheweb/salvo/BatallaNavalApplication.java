package com.codeoftheweb.salvo;

import com.codeoftheweb.salvo.models.Player;
import com.codeoftheweb.salvo.repositories.PlayerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BatallaNavalApplication {

	public static void main(String[] args) {
		SpringApplication.run(BatallaNavalApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(PlayerRepository playerRepository) {
		return (args) -> {
			// save a couple of customers
			Player player1 =playerRepository.save(new Player("Sofia", "Smenichelli@hotmail.com", "1234"));
			Player player2 = playerRepository.save(new Player("Santino", "santiPompei@hotmail.com", "5678"));
			Player player3 = playerRepository.save(new Player("Olivia", "olita@hotmail.com", "9876"));



		};
	}

}
