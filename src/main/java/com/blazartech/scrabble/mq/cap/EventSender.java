/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.blazartech.scrabble.mq.cap;

import com.blazartech.scrabble.data.app.ScrabbleData;

/**
 *
 * @author scott
 * @param <T>
 */
public interface EventSender {
    
    public void sendEvent(String topicName, ScrabbleData object);
}
