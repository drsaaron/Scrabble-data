/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.blazartech.scrabble.data.controller;

import com.blazartech.scrabble.data.app.Game;
import com.blazartech.scrabble.data.app.access.ScrabbleDataAccess;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author scott
 */
@RestController
@Slf4j
public class ScrabbleDataController {
    
    @Autowired
    private ScrabbleDataAccess dal;
    
    @PostMapping("/game")
    public Game addGame(@RequestBody Game g) {
        log.info("adding game {}", g);
        
        g = dal.addGame(g);
        return g;
    }
}
