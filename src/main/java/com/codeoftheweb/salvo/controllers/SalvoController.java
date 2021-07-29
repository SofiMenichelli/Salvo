package com.codeoftheweb.salvo.controllers;

import com.codeoftheweb.salvo.models.*;
import com.codeoftheweb.salvo.repositories.GamePlayerRepository;
import com.codeoftheweb.salvo.repositories.GameRepository;
import com.codeoftheweb.salvo.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class SalvoController {

    @Autowired
    GameRepository gameRepository;

    @Autowired
    GamePlayerRepository gamePlayerRepository;

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

   /* @RequestMapping("/games")
    public List<Long> getAllIds() {
        return gameRepository.findAll().stream().map(game -> game.getId()).collect(Collectors.toList());
    }*/

    //Solo muestro los id's de los juegos
  /* @RequestMapping("/games")
    public List<Long> getAllIds() {
        return gameRepository.findAll().stream().map(game -> game.getId()).collect(Collectors.toList());
    }*/

    private boolean isGuest(Authentication authentication) {
        return authentication == null || authentication instanceof AnonymousAuthenticationToken;
    }

    @GetMapping("/games")
    public Map<String, Object> login(Authentication authentication) {
        Map<String,Object> dto = new LinkedHashMap<>();
        if(isGuest(authentication)) {
            dto.put("player", null);
        } else {
            Player player = playerRepository.findByUser(authentication.getName());
            dto.put("player", makePlayerDTO(player));
        }
        dto.put("games", gameRepository.findAll().stream().map(this::makeGameDTO).collect(Collectors.toSet()));
        return dto;
    }

    @PostMapping("/players")
    public ResponseEntity<?> register(@RequestParam String user, @RequestParam String password) {

        if (user.isEmpty() || password.isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }
        if (playerRepository.findAll().contains(playerRepository.findByUser(user))) {
            return new ResponseEntity<>("Name already in use", HttpStatus.FORBIDDEN);
        }
        Player player = new Player(user,passwordEncoder.encode(password));
        playerRepository.save(player);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping("/games/{id}")
    public Map<String, Object> getGamesById(@PathVariable long id) {
          return makeGameDTO(gameRepository.findById(id).get());
    }

    @RequestMapping("/game_view/{id}")
    public Map<String, Object> getGamePlayerById(@PathVariable long id) {
        return makeGamePlayerDTO(gamePlayerRepository.findById(id).get());
    }

    public Map<String, Object> makeGameDTO(Game game) {
        Map<String, Object> gameDTO = new HashMap<>();
            gameDTO.put("Game Players", game.getGamePlayers().stream().map(this::makeGamePlayerDTO).collect(Collectors.toSet()));
            gameDTO.put("created", game.getDate());
            gameDTO.put("id", game.getId());
            gameDTO.put("ships", game.getGamePlayers().stream().map(ship -> ship.getShips().stream().map(this::makeShipDTO)));
            return gameDTO;
    }
    public Map<String, Object> makePlayerDTO(Player player) {
        Map<String, Object> playerDTO = new HashMap<>();
            playerDTO.put("id", player.getId());
            playerDTO.put("email", player.getUser());
            return playerDTO;
    }
    public Map<String, Object> makeGamePlayerDTO(GamePlayer gamePlayer) {
        Map<String, Object> gamePlayerDTO = new HashMap<>();
            gamePlayerDTO.put("id", gamePlayer.getId());
            gamePlayerDTO.put("player", makePlayerDTO(gamePlayer.getPlayer()));
            gamePlayerDTO.put("ships", gamePlayer.getShips().stream().map(this::makeShipDTO));
            gamePlayerDTO.put("salvoes", gamePlayer.getSalvos().stream().map(this::makeSalvoDTO));
            if (gamePlayer.getScore() != null)
                gamePlayerDTO.put("score", gamePlayer.getScore().getScore());
            else
                gamePlayerDTO.put("score", gamePlayer.getScore());
            return gamePlayerDTO;
    }
    public Map<String,Object> makeShipDTO(Ship ship) {
        Map<String, Object> shipDTO = new HashMap<>();
            shipDTO.put("Type", ship.getShipType());
            shipDTO.put("Locations", ship.getLocations());
            return  shipDTO;
    }
    public Map<String,Object> makeSalvoDTO(Salvo salvo) {
        Map<String, Object> salvoDTO = new HashMap<>();
            salvoDTO.put("turn", salvo.getTurn());
            salvoDTO.put("Locations", salvo.getSalvoLocations());
            return  salvoDTO;
    }
    public Map<String,Object> makeScoreDTO(Score score) {
        Map<String, Object> scoreDTO = new HashMap<>();
            scoreDTO.put("score", score.getScore());
            scoreDTO.put("fechaFinalizacion", score.getFinishDate());
            return  scoreDTO;
    }

}
