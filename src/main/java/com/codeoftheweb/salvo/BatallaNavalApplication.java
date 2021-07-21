package com.codeoftheweb.salvo;

import com.codeoftheweb.salvo.models.Game;
import com.codeoftheweb.salvo.models.GamePlayer;
import com.codeoftheweb.salvo.models.Player;
import com.codeoftheweb.salvo.repositories.GamePlayerRepository;
import com.codeoftheweb.salvo.repositories.GameRepository;
import com.codeoftheweb.salvo.repositories.PlayerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootApplication
public class BatallaNavalApplication {

	public static void main(String[] args) {
		SpringApplication.run(BatallaNavalApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(PlayerRepository playerRepository,
									  GameRepository gameRepository,
									  GamePlayerRepository gamePlayerRepository
									  ) {
		return (args) -> {
			// save a couple of customers
			Player player1 =playerRepository.save(new Player("Sofia", "Smenichelli@hotmail.com", "1234"));
			Player player2 = playerRepository.save(new Player("Santino", "santiPompei@hotmail.com", "5678"));
			Player player3 = playerRepository.save(new Player("Olivia", "olita@hotmail.com", "9876"));

			Game game1 = gameRepository.save(new Game(LocalDateTime.now()));
			Game game2 = gameRepository.save(new Game(LocalDateTime.now().plusHours(1)));
			Game game3 = gameRepository.save(new Game(LocalDateTime.now().plusHours(2)));

			GamePlayer gamePlayer = gamePlayerRepository.save(new GamePlayer(LocalDate.now(),game1, player1));
			GamePlayer gamePlayer2 = gamePlayerRepository.save(new GamePlayer(LocalDate.now(),game1, player2));

		};
	}

}
