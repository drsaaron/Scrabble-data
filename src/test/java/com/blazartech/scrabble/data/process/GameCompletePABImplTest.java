/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package com.blazartech.scrabble.data.process;

import com.blazartech.scrabble.data.app.Game;
import com.blazartech.scrabble.data.app.GamePlayer;
import com.blazartech.scrabble.data.app.GameStatus;
import com.blazartech.scrabble.data.app.access.ScrabbleDataAccess;
import com.blazartech.scrabble.data.app.access.ScrabbleDataAccessImpl;
import com.blazartech.scrabble.data.config.JpaVendorAdapterConfig;
import com.blazartech.scrabble.data.config.TransactionManagerConfig;
import com.blazartech.scrabble.data.entity.repos.TestDataSourceConfiguration;
import com.blazartech.scrabble.data.entity.repos.TestEntityManagerConfiguration;
import com.blazartech.scrabble.mq.cap.EventSender;
import jakarta.transaction.Transactional;
import java.util.Collection;
import lombok.extern.slf4j.Slf4j;
import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
    GameCompletePABImplTest.GameCompletePABImplTestConfiguration.class,
    TestEntityManagerConfiguration.class,
    TestDataSourceConfiguration.class,
    JpaVendorAdapterConfig.class,
    TransactionManagerConfig.class
})
@Transactional
@Slf4j
public class GameCompletePABImplTest {

    @Configuration
    @PropertySource("classpath:unittest.properties")
    static class GameCompletePABImplTestConfiguration {

        @Bean
        public GameCompletePABImpl instance() {
            return new GameCompletePABImpl();
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
    private GameCompletePABImpl instance;

    @Autowired
    private ScrabbleDataAccess dal;

    public GameCompletePABImplTest() {
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
     * Test of markGameComplete method, of class GameCompletePABImpl.
     */
    @Test
    @Sql("classpath:dalTest.sql")
    public void testMarkGameComplete() {
        log.info("markGameComplete");

        int gameId = 2;
        Game game = dal.getGame(gameId);
        Collection<GamePlayer> players = dal.getGamePlayersForGame(gameId);

        assertNull(game.getWinnerPlayerId());
        assertNull(game.getEndTimestamp());
        assertEquals(GameStatus.Playing, game.getGameStatus());

        players.stream()
                .map(p -> dal.getPlayer(p.getPlayerId()))
                .forEach(p -> assertNull(p.getHighGameId()));

        instance.markGameComplete(game);

        assertNotNull(game.getWinnerPlayerId());
        assertEquals(3, game.getWinnerPlayerId().intValue());
        assertNotNull(game.getEndTimestamp());
        assertEquals(GameStatus.Complete, game.getGameStatus());

        // high scores should be updated
        players = dal.getGamePlayersForGame(gameId);
        players.stream()
                .map(p -> dal.getPlayer(p.getPlayerId()))
                .forEach(p -> {
                    assertNotNull(p.getHighGameId());
                    assertEquals(gameId, p.getHighGameId().intValue());
                });
    }

    /**
     * Test of findHighestScorePlayer method, of class GameCompletePABImpl.
     */
    @Test
    @Sql("classpath:dalTest.sql")
    public void testFindHighestScorePlayer() {
        log.info("findHighestScorePlayer");

        int gameId = 2;

        Collection<GamePlayer> players = dal.getGamePlayersForGame(gameId);
        GamePlayer result = instance.findHighestScorePlayer(players);
        assertNotNull(result);
        assertEquals(3, result.getPlayerId());
    }

    @Test
    @Sql("classpath:dalTest.sql")
    public void testMarkGameComplete_invalidState() {
        log.info("markGameComplete_invalidState");

        assertThrows(IllegalStateException.class,
                () -> {
                    int gameId = 2;

                    Game game = dal.getGame(gameId);
                    game.setGameStatus(GameStatus.Complete);
                    instance.markGameComplete(game);
                }
        );
    }
}
