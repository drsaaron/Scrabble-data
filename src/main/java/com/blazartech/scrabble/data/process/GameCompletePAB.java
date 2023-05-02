/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.blazartech.scrabble.data.process;

import com.blazartech.scrabble.data.app.Game;

/**
 * implement all the processing needed to complete a game.  Really this processing
 * should be a bunch of asynchronous, event-driven processes.  But here we are.
 * @author scott
 */
public interface GameCompletePAB {
    
    void markGameComplete(Game g);
}
