/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.blazartech.scrabble.data.app.access;

import com.blazartech.scrabble.data.app.Game;
import com.blazartech.scrabble.data.app.GamePlayer;
import com.blazartech.scrabble.data.app.Player;
import java.util.Collection;

/**
 *
 * @author scott
 */
public interface ScrabbleDataAccess {
    
    public Game addGame(Game g);
    public void updateGame(Game g);
    public Game getGame(int gameId);
    
    public Collection<GamePlayer> getPlayersForGame(int gameId);
    
    public Player getPlayer(int playerId);
    public void updatePlayer(Player player);
}
