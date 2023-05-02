/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.blazartech.scrabble.data.app;

/**
 *
 * @author scott
 */
public enum GameStatus {
    
    Playing('P'), Complete('C');
    
    private char dbValue;
    GameStatus(char dbValue) {
        this.dbValue = dbValue;
    }
    
    public static GameStatus findByDBValue(char dbValue) {
        for (GameStatus s : values()) {
            if (s.dbValue == dbValue) {
                return s;
            }
        }
        
        throw new IllegalArgumentException("no value found for " + dbValue);
    }
    
    public char getDBValue() {
        return dbValue;
    }
}
