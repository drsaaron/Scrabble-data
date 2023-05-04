/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.blazartech.scrabble.data.controller;

import com.blazartech.scrabble.data.app.Game;
import com.blazartech.scrabble.data.app.GamePlayer;
import com.blazartech.scrabble.data.app.GamePlayerRound;
import com.blazartech.scrabble.data.app.Player;
import com.blazartech.scrabble.data.app.access.ScrabbleDataAccess;
import com.blazartech.scrabble.data.process.AddScorePAB;
import com.blazartech.scrabble.data.process.GameCompletePAB;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author scott
 */
@RestController
@Slf4j
public class ScrabbleDataController {
    
    @Autowired
    private ScrabbleDataAccess dal;
    
    @PostMapping("/player")
    @ResponseStatus(HttpStatus.CREATED)
    public Player addPlayer(@RequestBody Player player) {
        
        log.info("adding player {}", player);
        
        dal.addPlayer(player);
        
        return player;
    }
    
    @GetMapping("/player")
    @ResponseStatus(HttpStatus.OK)
    public List<Player> getAllPlayers() {
        
        log.info("getting all players");
        return dal.getPlayers();
    }
    
    @GetMapping("/player/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Player getPlayer(@PathVariable int id) {
        log.info("getting player {}", id);
        
        return dal.getPlayer(id);
    }
    
    @PostMapping("/game")
    @ResponseStatus(HttpStatus.CREATED)
    public Game addGame(@RequestBody Game g) {
        log.info("adding game {}", g);
        
        g = dal.addGame(g);
        return g;
    }
    
    @GetMapping("/game")
    @ResponseStatus(HttpStatus.OK)
    public List<Game> getAllGames() {
        log.info("getting all games");
        return dal.getAllGames();
    }
    
    @GetMapping("/game/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Game getGame(@PathVariable int id) {
        log.info("getting game {}", id);
        return dal.getGame(id);
    }
    
    @Autowired
    private GameCompletePAB gameComplete;
    
    @PutMapping("/game/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Game markGameComplete(@PathVariable int id, @RequestBody Game game) {
        log.info("updating game {}", id);
        
        gameComplete.markGameComplete(game); 
        return game;
    }
    
    @PostMapping("/gamePlayer") 
    @ResponseStatus(HttpStatus.CREATED)
    public GamePlayer addGamePlayer(@RequestBody GamePlayer gamePlayer) {
        log.info("adding gamePlayer {}", gamePlayer);
        
        return dal.addGamePlayer(gamePlayer);
    }
    
    @GetMapping("/gamePlayer")
    @ResponseStatus(HttpStatus.OK)
    public List<GamePlayer> getGamePlayersForGame(@RequestParam(required = true) int gameId) {
        log.info("getting players for game " + gameId);
        return dal.getGamePlayersForGame(gameId);
    }
    
    @Autowired
    private AddScorePAB addScorePAB;
    
    @PostMapping("/gamePlayerRound")
    @ResponseStatus(HttpStatus.CREATED)
    public GamePlayerRound addGamePlayerRound(@RequestBody GamePlayerRound gamePlayerRound) {
        log.info("adding game player round {}", gamePlayerRound);
        addScorePAB.addScoreToGame(gamePlayerRound);
        return gamePlayerRound;
    }
    
    @GetMapping("/gamePlayerRound")
    @ResponseStatus(HttpStatus.OK)
    public List<GamePlayerRound> getGamePlayerRoundsForGamePlayer(@RequestParam(required = true) int gamePlayerId) {
        log.info("getting player rounds for game player {}", gamePlayerId);
        return dal.getGamePlayerRoundsForGamePlayer(gamePlayerId);
    }
}
