/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.blazartech.scrabble.data.app;

import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * @author scott
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Game extends ScrabbleData {
    
    private GameStatus gameStatus;
    private Date startTimestamp;
    private Date endTimestamp;
    private Integer winnerPlayerId;
}
