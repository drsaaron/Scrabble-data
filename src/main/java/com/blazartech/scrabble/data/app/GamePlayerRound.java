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
public class GamePlayerRound extends ScrabbleData {
    
    private int gamePlayerId;
    private int score;
    
    private boolean isSevenLetter;
    private String notes;
    
    private Integer round;
    
    private Integer rollingScore;
}
