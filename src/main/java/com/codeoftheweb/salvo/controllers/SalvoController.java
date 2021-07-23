package com.codeoftheweb.salvo.controllers;

import com.codeoftheweb.salvo.models.*;
import com.codeoftheweb.salvo.repositories.GamePlayerRepository;
import com.codeoftheweb.salvo.repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
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

    @Autowired
    GamePlayerRepository gamePlayerRepository;

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
            gameDTO.put("Game Players", game.getGamePlayers().stream().map(gamePlayer -> makeGamePlayerDTO(gamePlayer)).collect(Collectors.toList()));
            gameDTO.put("created", game.getDate());
            gameDTO.put("id", game.getId());
            gameDTO.put("ships", game.getGamePlayers().stream().map(ship -> ship.getShips().stream().map(ship1 -> makeShipDTO(ship1))));
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
        gamePlayerDTO.put("ships", gamePlayer.getShips().stream().map(this::makeShipDTO).collect(Collectors.toList()));
        gamePlayerDTO.put("salvoes", gamePlayer.getSalvos().stream().map(this::makeSalvoDTO));
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






}
