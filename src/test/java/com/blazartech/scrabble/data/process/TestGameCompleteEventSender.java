/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.blazartech.scrabble.data.process;

import com.blazartech.scrabble.data.app.Game;
import com.blazartech.scrabble.data.app.ScrabbleData;
import com.blazartech.scrabble.mq.cap.EventSender;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author scott
 */
public class TestGameCompleteEventSender implements EventSender {

    @Autowired
    private GameCompleteHandler handler;
    
    @Override
    public void sendEvent(String topicName, ScrabbleData object) {
        handler.handleGameComplete((Game) object);
    }
    
}
