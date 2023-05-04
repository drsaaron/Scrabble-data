/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package com.blazartech.scrabble.data.controller;

import com.blazartech.scrabble.data.app.Game;
import com.blazartech.scrabble.data.app.GameStatus;
import com.blazartech.scrabble.data.app.Player;
import com.blazartech.scrabble.data.app.access.ScrabbleDataAccess;
import com.blazartech.scrabble.data.app.access.ScrabbleDataAccessImpl;
import com.blazartech.scrabble.data.config.JpaVendorAdapterConfig;
import com.blazartech.scrabble.data.config.TransactionManagerConfig;
import com.blazartech.scrabble.data.entity.repos.TestDataSourceConfiguration;
import com.blazartech.scrabble.data.entity.repos.TestEntityManagerConfiguration;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 *
 * @author scott
 */
@ExtendWith(SpringExtension.class)
@WebMvcTest(ScrabbleDataController.class)
@ContextConfiguration(classes = {
    ScrabbleDataControllerTest.ScrabbleDataControllerTestConfiguration.class,
    TestEntityManagerConfiguration.class,
    TestDataSourceConfiguration.class,
    JpaVendorAdapterConfig.class,
    TransactionManagerConfig.class
})
@Transactional
@Slf4j
public class ScrabbleDataControllerTest {

    @Configuration
    @PropertySource("classpath:unittest.properties")
    static class ScrabbleDataControllerTestConfiguration {

        @Bean
        public ScrabbleDataController instance() {
            return new ScrabbleDataController();
        }
        
        @Bean
        public ScrabbleDataAccess dal() {
            return new ScrabbleDataAccessImpl();
        }
    }

    @Autowired
    private MockMvc mockMvc;

    public ScrabbleDataControllerTest() {
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

    private static final ObjectMapper objectMapper = new ObjectMapper();

    static String asJsonString(final Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Test of addPlayer method, of class ScrabbleDataController.
     */
    @Test
    public void testAddPlayer() {
        log.info("addPlayer");

        Player player = new Player();
        player.setName("test");

        try {
            MvcResult result = mockMvc
                    .perform(
                            post("/player")
                                    .content(asJsonString(player))
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isCreated())
                    .andReturn();

            String response = result.getResponse().getContentAsString();
            Player createdPlayer = objectMapper.readValue(response, Player.class);

            assertNotNull(createdPlayer.getId());

        } catch (Exception e) {
            throw new RuntimeException("error running test: " + e.getMessage(), e);
        }

    }
    
    @Test
    @Sql("classpath:dalTest.sql")
    public void testGetAllPlayers() {
        log.info("getAllPlayers");
        
        try {
            MvcResult result = mockMvc
                    .perform(
                            get("/player")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .accept(MediaType.APPLICATION_JSON)
                    )
                    .andDo(print())
                    .andExpect(status().is2xxSuccessful())
                    .andReturn();

            String response = result.getResponse().getContentAsString();
            Player[] players = objectMapper.readValue(response, Player[].class);

            assertNotNull(players);
            assertEquals(4, players.length);

        } catch (Exception e) {
            throw new RuntimeException("error running test: " + e.getMessage(), e);
        }
    }

    @Test
    @Sql("classpath:dalTest.sql")
    public void testgetAllPlayers() {
        log.info("getAllPlayers");
        
        int playerId = 3;
        
        try {
            MvcResult result = mockMvc
                    .perform(
                            get("/player/" + Integer.toString(playerId))
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .accept(MediaType.APPLICATION_JSON)
                    )
                    .andDo(print())
                    .andExpect(status().is2xxSuccessful())
                    .andReturn();

            String response = result.getResponse().getContentAsString();
            Player player = objectMapper.readValue(response, Player.class);

            assertNotNull(player);
            assertEquals("Henrietta", player.getName());

        } catch (Exception e) {
            throw new RuntimeException("error running test: " + e.getMessage(), e);
        }
    }

    @Test
    public void testAddGame() {
        log.info("addGame");

        Game g = new Game();
        g.setGameStatus(GameStatus.Playing);

        try {
            MvcResult result = mockMvc
                    .perform(
                            post("/game")
                                    .content(asJsonString(g))
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isCreated())
                    .andReturn();

            String response = result.getResponse().getContentAsString();
            Game createdGame = objectMapper.readValue(response, Game.class);

            assertNotNull(createdGame.getId());
            assertNotNull(createdGame.getStartTimestamp());

        } catch (Exception e) {
            throw new RuntimeException("error running test: " + e.getMessage(), e);
        }

    }
    
    @Test
    @Sql("classpath:dalTest.sql")
    public void testgetAllGames() {
        log.info("getAllGames");

        try {
            MvcResult result = mockMvc
                    .perform(
                            get("/game")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .accept(MediaType.APPLICATION_JSON)
                    )
                    .andDo(print())
                    .andExpect(status().is2xxSuccessful())
                    .andReturn();

            String response = result.getResponse().getContentAsString();
            Game[] games = objectMapper.readValue(response, Game[].class);

            assertNotNull(games);
            assertEquals(2, games.length);

        } catch (Exception e) {
            throw new RuntimeException("error running test: " + e.getMessage(), e);
        }
    }

}
