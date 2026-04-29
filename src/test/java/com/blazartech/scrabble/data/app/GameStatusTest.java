/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.blazartech.scrabble.data.app;

import static com.blazartech.scrabble.data.app.GameStatus.Playing;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 *
 * @author scott
 */
@ExtendWith(SpringExtension.class)
@Slf4j
public class GameStatusTest {
    
    public GameStatusTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of findByDBValue method, of class GameStatus.
     */
    @Test
    public void testFindByDBValue() {
        log.info("findByDBValue");
        
        char dbValue = 'P';
        GameStatus expResult = Playing;
        GameStatus result = GameStatus.findByDBValue(dbValue);
        assertEquals(expResult, result);
    }

    @Test
    public void testFindByDBValue_invalid() {
        log.info("findByDBValue_invalid");
        
        char dbValue = 'Q';
        
        Exception e = assertThrows(IllegalArgumentException.class, () -> GameStatus.findByDBValue(dbValue));
        assertTrue(e.getMessage().startsWith("no enum found for db value"));
    }
    
}
