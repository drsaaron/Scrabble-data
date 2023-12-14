/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.blazartech.scrabble.data.process;

import com.blazartech.scrabble.data.app.GamePlayerRound;
import com.blazartech.scrabble.data.app.access.ScrabbleDataAccess;
import com.blazartech.scrabble.mq.cap.EventSender;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 *
 * @author scott
 */
@Service
@Slf4j
public class AddScorePABImpl implements AddScorePAB {

    @Autowired
    private ScrabbleDataAccess dal;
    
    @Autowired
    private EventSender eventSender;
    
    @Value("${scrabble.mq.rabbit.gameplayerroundadded.topicName}")
    private String topicName;
    
    @Override
    @Transactional
    public void addScoreToGame(GamePlayerRound round) {
        log.info("adding score to game: {}", round);
        
        // save the round
        dal.addGamePlayerRound(round);
        
        // send an event for subsequentprocessing
        eventSender.sendEvent(topicName, round);
    }
    
}
