package com.codeoftheweb.salvo.controllers;

import com.codeoftheweb.salvo.models.Game;
import com.codeoftheweb.salvo.models.GamePlayer;
import com.codeoftheweb.salvo.models.Player;
import com.codeoftheweb.salvo.repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class SalvoController {

    @Autowired
    GameRepository gameRepository;

   /* @RequestMapping("/games")
    public List<Long> getAllIds() {
        return gameRepository.findAll().stream().map(game -> game.getId()).collect(Collectors.toList());
    }*/

    //Solo muestro los id's de los juegos
  /* @RequestMapping("/games")
    public List<Long> getAllIds() {
        return gameRepository.findAll().stream().map(game -> game.getId()).collect(Collectors.toList());
    }*/

    @RequestMapping("/games")
    public List<Map<String, Object>> getGames() {
        return gameRepository.findAll().stream().map(game -> makeGameDTO(game)).collect(Collectors.toList());

    }
    public Map<String, Object> makeGameDTO(Game game) {
        Map<String, Object> gameDTO = new HashMap<>();
            gameDTO.put("Game Players", game.getGamePlayers().stream().map(gamePlayer -> makeGamePlayerDTO(gamePlayer)).collect(Collectors.toList()));
            gameDTO.put("created", game.getDate());
            gameDTO.put("id", game.getId());
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
        return gamePlayerDTO;
    }






}
