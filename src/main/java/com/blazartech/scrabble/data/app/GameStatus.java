/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.blazartech.scrabble.data.app;

import com.blazartech.enumwithdbvaluebacking.EnumWithDBValueBacking;

/**
 *
 * @author scott
 */
public enum GameStatus implements EnumWithDBValueBacking<String> {
    
    Playing("P"), Complete("C");
    
    private final String dbValue;
    
    private GameStatus(String dbValue) {
        this.dbValue = dbValue;
    }
    
    public static GameStatus findByDBValue(String dbValue) {
        return EnumWithDBValueBacking.getFromDBValue(GameStatus.class, dbValue);
    }
    
    @Override
    public String getDBValue() {
        return dbValue;
    }
}
