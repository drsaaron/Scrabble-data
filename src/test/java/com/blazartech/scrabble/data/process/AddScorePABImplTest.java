/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package com.blazartech.scrabble.data.process;

import com.blazartech.scrabble.data.app.GamePlayer;
import com.blazartech.scrabble.data.app.GamePlayerRound;
import com.blazartech.scrabble.data.app.access.ScrabbleDataAccess;
import com.blazartech.scrabble.data.app.access.ScrabbleDataAccessImpl;
import com.blazartech.scrabble.data.config.JpaVendorAdapterConfig;
import com.blazartech.scrabble.data.config.TransactionManagerConfig;
import com.blazartech.scrabble.data.entity.repos.TestDataSourceConfiguration;
import com.blazartech.scrabble.data.entity.repos.TestEntityManagerConfiguration;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 *
 * @author scott
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
    AddScorePABImplTest.AddScorePABImplTestConfiguration.class,
    TestEntityManagerConfiguration.class,
    TestDataSourceConfiguration.class,
    JpaVendorAdapterConfig.class,
    TransactionManagerConfig.class
})
@Transactional
@Slf4j
public class AddScorePABImplTest {
    
    @Configuration
    @PropertySource("classpath:unittest.properties")
    static class AddScorePABImplTestConfiguration {
        
        @Bean
        public AddScorePABImpl instance() {
            return new AddScorePABImpl();
        }

        @Bean
        public ScrabbleDataAccess dal() {
            return new ScrabbleDataAccessImpl();
        }
    }
    
    @Autowired
    private AddScorePABImpl instance;
    
    @Autowired
    private ScrabbleDataAccess dal;
    
    public AddScorePABImplTest() {
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
     * Test of addScoreToGame method, of class AddScorePABImpl.
     */
    @Test
    @Sql("classpath:addScoreTest.sql")
    public void testAddScoreToGame() {
        log.info("addScoreToGame");
        
        int gamePlayer = 1;
        int score = 55;
        
        GamePlayerRound round = new GamePlayerRound();
        round.setGamePlayerId(gamePlayer);
        round.setNotes("I am a test");
        round.setScore(score);
        round.setSevenLetter(true);
        round.setRound(1);

        assertNull(round.getId());
        
        instance.addScoreToGame(round);

        assertNotNull(round.getId());
        
        GamePlayer player = dal.getGamePlayer(gamePlayer);
        assertEquals(score, player.getScore());
        
        // add a second score
        GamePlayerRound round2 = new GamePlayerRound();
        round2.setGamePlayerId(gamePlayer);
        round2.setNotes("more notes");
        round2.setScore(score + 10);
        round2.setSevenLetter(false);
        round2.setRound(2);
        
        instance.addScoreToGame(round2);
        
        player = dal.getGamePlayer(gamePlayer);
        assertEquals(score + score + 10, player.getScore());
    }
    
}
