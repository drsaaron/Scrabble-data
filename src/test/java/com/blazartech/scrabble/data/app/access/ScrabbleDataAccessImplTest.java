/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package com.blazartech.scrabble.data.app.access;

import com.blazartech.scrabble.data.app.Game;
import com.blazartech.scrabble.data.app.GamePlayer;
import com.blazartech.scrabble.data.app.GameStatus;
import com.blazartech.scrabble.data.config.JpaVendorAdapterConfig;
import com.blazartech.scrabble.data.config.TransactionManagerConfig;
import com.blazartech.scrabble.data.entity.repos.TestDataSourceConfiguration;
import com.blazartech.scrabble.data.entity.repos.TestEntityManagerConfiguration;
import jakarta.transaction.Transactional;
import java.util.Collection;
import lombok.extern.slf4j.Slf4j;
import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
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
    ScrabbleDataAccessImplTest.ScrabbleDataAccessImplTestConfiguration.class,
    TestEntityManagerConfiguration.class,
    TestDataSourceConfiguration.class,
    JpaVendorAdapterConfig.class,
    TransactionManagerConfig.class
})
@Transactional
@Slf4j
public class ScrabbleDataAccessImplTest {
    
    @Configuration
    @PropertySource("classpath:unittest.properties")
    static class ScrabbleDataAccessImplTestConfiguration {
        
        @Bean
        public ScrabbleDataAccessImpl instance() {
            return new ScrabbleDataAccessImpl();
        }
    }
    
    @Autowired
    private ScrabbleDataAccessImpl instance;
    
    public ScrabbleDataAccessImplTest() {
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
     * Test of getGame method, of class ScrabbleDataAccessImpl.
     */
    @Test
    @Sql("classpath:dalTest.sql")
    public void testGetGame() {
        log.info("getGame");
        int gameId = 1;

        Game result = instance.getGame(gameId);
        assertEquals(GameStatus.Playing, result.getGameStatus());
    }

    /**
     * Test of getPlayersForGame method, of class ScrabbleDataAccessImpl.
     */
    @Test
    @Sql("classpath:dalTest.sql")
    public void testGetPlayersForGame() {
        log.info("getPlayersForGame");
        int gameId = 1;

        Collection<GamePlayer> result = instance.getPlayersForGame(gameId);
        assertEquals(2, result.size());
        
        // the first player should be player 2 according to the sequencing.
        GamePlayer firstPlayer = result.iterator().next();
        assertEquals(2, firstPlayer.getPlayerId());
        assertEquals(1, firstPlayer.getSequenceNumber());
    }
    
}
