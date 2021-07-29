package com.codeoftheweb.salvo;

import com.codeoftheweb.salvo.models.*;
import com.codeoftheweb.salvo.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

@SpringBootApplication
public class BatallaNavalApplication {
	@Autowired
	PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(BatallaNavalApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(PlayerRepository playerRepository,
									  GameRepository gameRepository,
									  GamePlayerRepository gamePlayerRepository,
									  ShipRepository shipRepository,
									  SalvoRepository salvoRepository,
									  ScoreRepository scoreRepository
									  ) {
		return (args) -> {
			// save a couple of customers
			Player player1 =playerRepository.save(new Player("j.bauer@ctu.gov", passwordEncoder.encode("24")));
			Player player2 = playerRepository.save(new Player("c.obrian@ctu.gov", passwordEncoder.encode("42")));
			Player player3 = playerRepository.save(new Player("kim_bauer@gmail.com", passwordEncoder.encode("kb")));
			Player player4 = playerRepository.save(new Player("t.almeida@ctu.gov", passwordEncoder.encode("mole")));


			Game game1 = gameRepository.save(new Game(LocalDateTime.now()));
			Game game2 = gameRepository.save(new Game(LocalDateTime.now().plusHours(1)));
			Game game3 = gameRepository.save(new Game(LocalDateTime.now().plusHours(2)));

			GamePlayer gamePlayer = gamePlayerRepository.save(new GamePlayer(LocalDate.now(),game1, player1));
			GamePlayer gamePlayer2 = gamePlayerRepository.save(new GamePlayer(LocalDate.now(),game1, player2));
			GamePlayer gamePlayer3 = gamePlayerRepository.save(new GamePlayer(LocalDate.now(),game2, player1));
			GamePlayer gamePlayer4 = gamePlayerRepository.save(new GamePlayer(LocalDate.now(),game2, player2));
			GamePlayer gamePlayer5 = gamePlayerRepository.save(new GamePlayer(LocalDate.now(),game3, player1));
			GamePlayer gamePlayer6 = gamePlayerRepository.save(new GamePlayer(LocalDate.now(),game3, player4));

			Ship shipCarrier = shipRepository.save(new Ship(ShipType.Carrier, Arrays.asList("H2","H3","H4","H5","H6"),gamePlayer));
			Ship shipBattleship = shipRepository.save(new Ship(ShipType.Battleship, Arrays.asList("C1","D1","E1","F1"), gamePlayer));
			Ship shipDestroyer = shipRepository.save(new Ship(ShipType.Destroyer, Arrays.asList("J2","J3","J4"),gamePlayer));
			Ship shipCruiser2 = shipRepository.save(new Ship(ShipType.Cruiser, Arrays.asList("A10","B10","C10"),gamePlayer2));
			Ship shipPatrolBoat2 = shipRepository.save(new Ship(ShipType.PatrolBoat, Arrays.asList("B4","B5"), gamePlayer2));
			Ship shipBattleship2 = shipRepository.save(new Ship(ShipType.Battleship, Arrays.asList("C4","C5","C6","C7"), gamePlayer2));


			Salvo salvo = salvoRepository.save(new Salvo(gamePlayer, Arrays.asList("B5", "C5", "F1"), 1));
			Salvo salvo2 = salvoRepository.save(new Salvo(gamePlayer, Arrays.asList("F2", "D5"), 2));
			Salvo salvo3 = salvoRepository.save(new Salvo(gamePlayer2, Arrays.asList("B4", "B5", "B6"), 1));
			Salvo salvo4 = salvoRepository.save(new Salvo(gamePlayer2, Arrays.asList("E1", "H3", "A2"), 2));

			scoreRepository.save(new Score(1, LocalDateTime.now(), player1, game1));
			scoreRepository.save(new Score(0, LocalDateTime.now(), player2, game1));
			scoreRepository.save(new Score(0, LocalDateTime.now(), player1, game2));
			scoreRepository.save(new Score(1, LocalDateTime.now(), player2, game2));
			scoreRepository.save(new Score(0.5, LocalDateTime.now(), player1, game3));
			scoreRepository.save(new Score(0.5, LocalDateTime.now(), player4, game3));

		};
	}

}
