/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.blazartech.scrabble.data.app;

import java.util.stream.Stream;

/**
 *
 * @author scott
 */
public enum GameStatus {
    
    Playing('P'), Complete('C');
    
    private final char dbValue;
    
    private GameStatus(char dbValue) {
        this.dbValue = dbValue;
    }
    
    public static GameStatus findByDBValue(char dbValue) {
        return Stream.of(values())
                .filter(v -> v.getDBValue() == dbValue)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("no value found for " + dbValue));
    }
    
    public char getDBValue() {
        return dbValue;
    }
}
