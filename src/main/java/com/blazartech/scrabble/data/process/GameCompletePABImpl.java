/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.blazartech.scrabble.data.process;

import com.blazartech.scrabble.data.app.Game;
import com.blazartech.scrabble.data.app.GamePlayer;
import com.blazartech.scrabble.data.app.GameStatus;
import com.blazartech.scrabble.data.app.access.ScrabbleDataAccess;
import com.blazartech.scrabble.mq.cap.EventSender;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/**
 *
 * @author scott
 */
@Service
@Slf4j
@Profile("!build")
public class GameCompletePABImpl implements GameCompletePAB {

    @Autowired
    private ScrabbleDataAccess dal;
    
    @Autowired
    private EventSender eventSender;
    
    @Value("${scrabble.mq.rabbit.gamecompleted.topicName}")
    private String topicName;
    
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
        
        // send event for subsequent processing
        eventSender.sendEvent(topicName, g);
    }
    
    public GamePlayer findHighestScorePlayer(Collection<GamePlayer> players) {
        List<GamePlayer> playersList = new ArrayList<>(players);
        Collections.sort(playersList, (p1, p2) -> Integer.compare(p2.getScore(), p1.getScore()));
        return playersList.get(0);
    }
    
}
