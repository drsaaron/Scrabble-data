/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package com.blazartech.scrabble.data.process;

import com.blazartech.scrabble.data.app.GamePlayerRound;
import com.blazartech.scrabble.data.app.access.ScrabbleDataAccess;
import com.blazartech.scrabble.data.app.access.ScrabbleDataAccessImpl;
import com.blazartech.scrabble.data.config.JpaVendorAdapterConfig;
import com.blazartech.scrabble.data.config.TransactionManagerConfig;
import com.blazartech.scrabble.data.entity.repos.TestDataSourceConfiguration;
import com.blazartech.scrabble.data.entity.repos.TestEntityManagerConfiguration;
import com.blazartech.scrabble.mq.cap.EventSender;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
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
    GamePlayerRoundAddedHandlerImplTest.GamePlayerRoundAddedHandlerImplTestConfiguration.class,
    TestEntityManagerConfiguration.class,
    TestDataSourceConfiguration.class,
    JpaVendorAdapterConfig.class,
    TransactionManagerConfig.class
})
@Transactional
public class GamePlayerRoundAddedHandlerImplTest {
    
    private static final Logger log = LoggerFactory.getLogger(GamePlayerRoundAddedHandlerImplTest.class);
    
    @Configuration
    @PropertySource("classpath:unittest.properties")
    static class GamePlayerRoundAddedHandlerImplTestConfiguration {

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
    private GamePlayerRoundAddedHandlerImpl instance;
    
    @Autowired
    private ScrabbleDataAccess dal;
    
    public GamePlayerRoundAddedHandlerImplTest() {
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
     * Test of handleGamePlayerRoundAdded method, of class GamePlayerRoundAddedHandlerImpl.
     */
    @Test
    @Sql("classpath:dalTest.sql")
    public void testHandleGamePlayerRoundAdded() {
        System.out.println("handleGamePlayerRoundAdded");
        
        // read round 1001, which is the final round in the test setup
        int gamePlayerRoundId = 1001;
        int expectedScore = 125;
        GamePlayerRound round = dal.getGamePlayerRound(gamePlayerRoundId);
        assertNull(round.getRollingScore());
        
        instance.handleGamePlayerRoundAdded(round);
        
        // check that the object is updated.
        assertNotNull(round.getRollingScore());
        assertEquals(expectedScore, round.getRollingScore());
        
        // re-read from DB and validate it's been updated there too
    /*    GamePlayerRound updatedRound = dal.getGamePlayerRound(gamePlayerRoundId);
        assertNotNull(updatedRound.getRollingScore());
        assertEquals(expectedScore, updatedRound.getRollingScore());*/
    }
    
}
