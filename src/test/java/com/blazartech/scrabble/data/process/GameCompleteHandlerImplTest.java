/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package com.blazartech.scrabble.data.process;

import com.blazartech.scrabble.data.app.GamePlayer;
import com.blazartech.scrabble.data.app.GamePlayerRound;
import com.blazartech.scrabble.data.app.Player;
import com.blazartech.scrabble.data.app.access.ScrabbleDataAccess;
import com.blazartech.scrabble.data.app.access.ScrabbleDataAccessImpl;
import com.blazartech.scrabble.data.config.JpaVendorAdapterConfig;
import com.blazartech.scrabble.data.config.TransactionManagerConfig;
import com.blazartech.scrabble.data.entity.repos.TestDataSourceConfiguration;
import com.blazartech.scrabble.data.entity.repos.TestEntityManagerConfiguration;
import com.blazartech.scrabble.mq.cap.EventSender;
import jakarta.transaction.Transactional;
import java.util.Collection;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    GameCompleteHandlerImplTest.GameCompleteHandlerImplTestConfiguration.class,
    TestEntityManagerConfiguration.class,
    TestDataSourceConfiguration.class,
    JpaVendorAdapterConfig.class,
    TransactionManagerConfig.class
})
@Transactional
public class GameCompleteHandlerImplTest {
    
    private static final Logger log = LoggerFactory.getLogger(GameCompleteHandlerImplTest.class);
    
    @Configuration
    @PropertySource("classpath:unittest.properties")
    static class GameCompleteHandlerImplTestConfiguration {

        @Bean
        public GamePlayerRoundAddedHandlerImpl instance() {
            return new GamePlayerRoundAddedHandlerImpl();
        }

        @Bean
        public ScrabbleDataAccess dal() {
            return new ScrabbleDataAccessImpl();
        }
        
        @Bean
        public EventSender eventSender() {
            return new TestGameCompleteEventSender();
        }
        
        @Bean
        public GameCompleteHandler handler() {
            return new GameCompleteHandlerImpl();
        }
    }
    
    @Autowired
    private GameCompleteHandlerImpl instance;
    
    @Autowired
    private ScrabbleDataAccess dal;
    
    public GameCompleteHandlerImplTest() {
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

    private static final int GAME_PLAYER_ID = 7;
    
    /**
     * Test of getTotalScore method, of class GameCompleteHandlerImpl.
     */
    @Test
    @Sql("classpath:dalTest.sql")
    public void testGetTotalScore() {
        log.info("getTotalScore");
        
        Collection<GamePlayerRound> rounds = dal.getGamePlayerRoundsForGamePlayer(GAME_PLAYER_ID);
        int expResult = 125;
        int result = instance.getTotalScore(rounds);
        assertEquals(expResult, result);
    }

    private static final int GAME_PLAYER_ID_NO_PREVIOUS_HIGH_GAME = 8;
    
    /**
     * Test of isNewHighScore method, of class GameCompleteHandlerImpl.
     */
    @Test
    @Sql("classpath:dalTest.sql")
    public void testIsNewHighScore_noPrevious() {
        log.info("isNewHighScore_noPrevious");
        
        GamePlayer gp = dal.getGamePlayer(GAME_PLAYER_ID_NO_PREVIOUS_HIGH_GAME );
        Player p = dal.getPlayer(gp.getPlayerId());
        
        int score = 55;

        boolean expResult = true;
        boolean result = instance.isNewHighScore(p, score);
        assertEquals(expResult, result);
    }
    
    @Test
    @Sql("classpath:dalTest.sql")
    public void testIsNewHighScore_withPrevious() {
        log.info("isNewHighScore_withPrevious");
        
        GamePlayer gp = dal.getGamePlayer(GAME_PLAYER_ID);
        Player p = dal.getPlayer(gp.getPlayerId());
        
        int score = 255;

        boolean expResult = true;
        boolean result = instance.isNewHighScore(p, score);
        assertEquals(expResult, result);
    }

    
}
