/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.blazartech.scrabble.data.app;

import java.util.Date;
import lombok.Data;

/**
 *
 * @author scott
 */
@Data
public class Game {
    
    private int id;
    private GameStatus gameStatus;
    private Date startTimestamp;
    private Date endTimestamp;
    private Integer winnerPlayerId;
}
