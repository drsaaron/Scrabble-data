/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.blazartech.scrabble.data.app.access;

import com.blazartech.scrabble.data.app.Game;
import com.blazartech.scrabble.data.app.GamePlayer;
import com.blazartech.scrabble.data.app.GamePlayerRound;
import com.blazartech.scrabble.data.app.Player;
import java.util.List;

/**
 *
 * @author scott
 */
public interface ScrabbleDataAccess {
    
    public Game addGame(Game g);
    public void updateGame(Game g);
    public Game getGame(int gameId);
    public List<Game> getAllGames();
    
    public List<GamePlayer> getPlayersForGame(int gameId);
    public GamePlayer getPlayerForGame(int gameId, int playerId);
    public GamePlayer getGamePlayer(int id);
    public void updateGamePlayer(GamePlayer gamePlayer);
    public GamePlayer addGamePlayer(GamePlayer gamePlayer);
    
    public Player addPlayer(Player player);
    public Player getPlayer(int playerId);
    public void updatePlayer(Player player);
    public List<Player> getPlayers();
    
    public GamePlayerRound addGamePlayerRound(GamePlayerRound round);
}
