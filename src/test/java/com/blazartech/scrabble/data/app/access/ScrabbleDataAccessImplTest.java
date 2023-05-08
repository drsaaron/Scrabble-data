/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package com.blazartech.scrabble.data.app.access;

import com.blazartech.scrabble.data.app.Game;
import com.blazartech.scrabble.data.app.GamePlayer;
import com.blazartech.scrabble.data.app.GamePlayerRound;
import com.blazartech.scrabble.data.app.GameStatus;
import com.blazartech.scrabble.data.app.Player;
import com.blazartech.scrabble.data.config.JpaVendorAdapterConfig;
import com.blazartech.scrabble.data.config.TransactionManagerConfig;
import com.blazartech.scrabble.data.entity.repos.TestDataSourceConfiguration;
import com.blazartech.scrabble.data.entity.repos.TestEntityManagerConfiguration;
import jakarta.transaction.Transactional;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

        Collection<GamePlayer> result = instance.getGamePlayersForGame(gameId);
        assertEquals(2, result.size());

        // the first player should be player 2 according to the sequencing.
        GamePlayer firstPlayer = result.iterator().next();
        assertEquals(2, firstPlayer.getPlayerId());
        assertEquals(1, firstPlayer.getSequenceNumber());
    }

    @Test
    @Sql("classpath:dalAddTest.sql")
    public void testAddGame() {
        log.info("addGame");

        Game g = new Game();
        g.setGameStatus(GameStatus.Playing);

        Game newGame = instance.addGame(g);

        assertNotNull(newGame.getId());
        assertNotNull(newGame.getStartTimestamp());
    }

    @Test
    @Sql("classpath:dalTest.sql")
    public void testUpdateGame() {
        log.info("addGame");

        int gameNumber = 1;

        Game g = instance.getGame(gameNumber);

        g.setGameStatus(GameStatus.Complete);
        g.setEndTimestamp(new Date());

        instance.updateGame(g);

        assertNotNull(g.getId());
        assertNotNull(g.getStartTimestamp());

        Game g2 = instance.getGame(gameNumber);
        assertNotNull(g2.getEndTimestamp());
    }

    @Test
    @Sql("classpath:dalAddTest.sql")
    public void testAddPlayer() {
        log.info("testAddPlayer");

        Player p = new Player();
        p.setName("tester");

        instance.addPlayer(p);

        assertNotNull(p.getId());
    }

    @Test
    @Sql("classpath:dalTest.sql")
    public void testGetPlayers() {
        log.info("testGetPlayers");

        List<Player> players = instance.getPlayers();

        assertNotNull(players);
        assertEquals(4, players.size());
    }

    @Test
    @Sql("classpath:dalTest.sql")
    public void testGetGamePlayerRoundsForGamePlayer() {
        log.info("getGamePlayerRoundsForGamePlayer");

        int gamePlayerId = 7;

        List<GamePlayerRound> rounds = instance.getGamePlayerRoundsForGamePlayer(gamePlayerId);

        assertEquals(3, rounds.size());
    }

    @Test
    @Sql("classpath:dalTest.sql")
    public void testGetGamePlayerForGame() {
        log.info("getGamePlayerForGame");
        
        int gameId = 2;
        int playerId = 2;
        
        GamePlayer player = instance.getGamePlayerForGame(gameId, playerId);
        
        assertNotNull(player);
        assertEquals(1, player.getSequenceNumber());
    }
}
