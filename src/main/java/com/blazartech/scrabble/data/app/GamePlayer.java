/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.blazartech.scrabble.data.app;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 *
 * @author scott
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class GamePlayer extends ScrabbleData {
    
    private int gameId;
    private int playerId;
    private int score;
    private int sequenceNumber;
    
}
