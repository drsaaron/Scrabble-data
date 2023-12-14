/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.blazartech.scrabble.data.process;

import com.blazartech.scrabble.data.app.GamePlayer;
import com.blazartech.scrabble.data.app.GamePlayerRound;
import com.blazartech.scrabble.data.app.access.ScrabbleDataAccess;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author scott
 */
@Component
@Slf4j
public class GamePlayerRoundAddedHandlerImpl implements GamePlayerRoundAddedHandler {

    @Autowired
    private ScrabbleDataAccess dal;

    @Transactional
    @Override
    public void handleGamePlayerRoundAdded(GamePlayerRound round) {
        log.info("updating score for playerRound {}", round);
        
        // update the player's score
        GamePlayer gamePlayer = dal.getGamePlayer(round.getGamePlayerId());
        int currentScore = gamePlayer.getScore();
        int newScore = currentScore + round.getScore();
        gamePlayer.setScore(newScore);
        dal.updateGamePlayer(gamePlayer);
    }
}
