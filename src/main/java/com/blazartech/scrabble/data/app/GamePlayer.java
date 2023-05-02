/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.blazartech.scrabble.data.app;

import lombok.Data;

/**
 *
 * @author scott
 */
@Data
public class GamePlayer {
    
    private int id;
    private int gameId;
    private int playerId;
    private int score;
    private int sequenceNumber;
}
