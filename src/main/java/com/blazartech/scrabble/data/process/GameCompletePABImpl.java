/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.blazartech.scrabble.data.process;

import com.blazartech.scrabble.data.app.Game;
import com.blazartech.scrabble.data.app.GamePlayer;
import com.blazartech.scrabble.data.app.GameStatus;
import com.blazartech.scrabble.data.app.Player;
import com.blazartech.scrabble.data.app.access.ScrabbleDataAccess;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author scott
 */
@Service
@Slf4j
public class GameCompletePABImpl implements GameCompletePAB {

    @Autowired
    private ScrabbleDataAccess dal;
    
    @Override
    @Transactional
    public void markGameComplete(Game g) {
        log.info("marking game {} complete", g.getId());
        
        // sanity check on data.  Status should indicate playing, but no
        // completion date.
        if (g.getGameStatus() != GameStatus.Playing) {
            throw new IllegalStateException("game is not in progress");
        }
        if (g.getEndTimestamp() != null) {
            throw new IllegalStateException("game is already marked complete");
        }
        
        // update the timestamp & status
        g.setGameStatus(GameStatus.Complete);
        g.setEndTimestamp(new Date());
        
        // who won?
        Collection<GamePlayer> players = dal.getGamePlayersForGame(g.getId());
        GamePlayer highestScorePlayer = findHighestScorePlayer(players);
        int winner = highestScorePlayer.getPlayerId();
        g.setWinnerPlayerId(winner);
        dal.updateGame(g);
        
        // for each player, is this their highest score?  
        for (GamePlayer player : players) {
            int score = player.getScore();
            Player p = dal.getPlayer(player.getPlayerId());
            if (p.getHighGameId() != null) {
                GamePlayer highGamePlayer = dal.getGamePlayerForGame(p.getHighGameId(), p.getId());
                if (score > highGamePlayer.getScore()) {
                    // new high score
                    p.setHighGameId(g.getId());
                    dal.updatePlayer(p);
                }
            } else { // no existing high game, so by definition this is the high game
                log.info("high game updated");
                p.setHighGameId(g.getId());
                dal.updatePlayer(p);
            }
        }
        
        // update
        dal.updateGame(g);
    }
    
    public GamePlayer findHighestScorePlayer(Collection<GamePlayer> players) {
        List<GamePlayer> playersList = new ArrayList(players);
        Collections.sort(playersList, (p1, p2) -> Integer.compare(p2.getScore(), p1.getScore()));
        return playersList.get(0);
    }
    
}
