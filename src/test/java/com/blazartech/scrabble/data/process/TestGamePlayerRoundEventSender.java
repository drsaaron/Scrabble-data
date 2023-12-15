/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.blazartech.scrabble.data.process;

import com.blazartech.scrabble.data.app.GamePlayerRound;
import com.blazartech.scrabble.data.app.ScrabbleData;
import com.blazartech.scrabble.mq.cap.EventSender;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * a mock event sender that will directly invoke the update handler
 * @author scott
 */
public class TestGamePlayerRoundEventSender implements EventSender {
    
    @Autowired
    private GamePlayerRoundAddedHandler handler;

    @Override
    public void sendEvent(String topicName, ScrabbleData object) {
        handler.handleGamePlayerRoundAdded((GamePlayerRound) object);
    }
}
