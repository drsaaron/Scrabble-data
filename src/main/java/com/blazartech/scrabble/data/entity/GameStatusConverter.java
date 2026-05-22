/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.blazartech.scrabble.data.entity;

import com.blazartech.enumwithdbvaluebacking.EnumWithDBValueBackingConverter;
import com.blazartech.scrabble.data.app.GameStatus;
import jakarta.persistence.Converter;

/**
 *
 * @author scott
 */
@Converter
public class GameStatusConverter extends EnumWithDBValueBackingConverter<GameStatus, String> {
    
    public GameStatusConverter() {
        super(GameStatus.class);
    }
    
}
