/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.blazartech.scrabble.data.process;

import com.blazartech.scrabble.data.app.Game;
import com.blazartech.scrabble.data.app.GamePlayer;
import com.blazartech.scrabble.data.app.Player;
import com.blazartech.scrabble.data.app.access.ScrabbleDataAccess;
import jakarta.transaction.Transactional;
import java.util.Collection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author scott
 */
@Component
@Slf4j
public class GameCompleteHandlerImpl implements GameCompleteHandler {

    @Autowired
    private ScrabbleDataAccess dal;
    
    @Override
    @Transactional
    public void handleGameComplete(Game game) {
        log.info("handling game complete for game {}", game);
        
        // for each player, is this their highest score?  
        Collection<GamePlayer> players = dal.getGamePlayersForGame(game.getId());
        for (GamePlayer player : players) {
            int score = player.getScore();
            Player p = dal.getPlayer(player.getPlayerId());
            if (p.getHighGameId() != null) {
                GamePlayer highGamePlayer = dal.getGamePlayerForGame(p.getHighGameId(), p.getId());
                if (score > highGamePlayer.getScore()) {
                    // new high score
                    p.setHighGameId(game.getId());
                    dal.updatePlayer(p);
                }
            } else { // no existing high game, so by definition this is the high game
                log.info("high game updated");
                p.setHighGameId(game.getId());
                dal.updatePlayer(p);
            }
        }
    }
    
}
