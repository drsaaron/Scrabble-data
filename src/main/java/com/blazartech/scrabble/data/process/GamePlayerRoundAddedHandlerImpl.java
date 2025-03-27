/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.blazartech.scrabble.data.process;

import com.blazartech.scrabble.data.app.GamePlayerRound;
import com.blazartech.scrabble.data.app.access.ScrabbleDataAccess;
import jakarta.transaction.Transactional;
import java.util.Collection;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author scott
 */
@Component
public class GamePlayerRoundAddedHandlerImpl implements GamePlayerRoundAddedHandler {

    private static final Logger log = LoggerFactory.getLogger(GamePlayerRoundAddedHandlerImpl.class);
    
    @Autowired
    private ScrabbleDataAccess dal;

    @Transactional
    @Override
    public void handleGamePlayerRoundAdded(GamePlayerRound round) {
        log.info("updating score for playerRound {}", round);
        
        // update the rolling score
        Collection<GamePlayerRound> previousRounds = dal.getGamePlayerRoundsForGamePlayer(round.getGamePlayerId());
        int cummulativeScore = previousRounds.stream()
                .filter(r -> r.getRound() <= round.getRound())
                .map(r -> r.getScore())
                .collect(Collectors.summingInt(Integer::intValue));
        round.setRollingScore(cummulativeScore);
        dal.updateGamePlayerRound(round);
    }
}
