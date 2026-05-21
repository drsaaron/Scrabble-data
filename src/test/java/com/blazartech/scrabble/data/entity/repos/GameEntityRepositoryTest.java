/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.blazartech.scrabble.data.entity.repos;

import com.blazartech.scrabble.data.app.GameStatus;
import com.blazartech.scrabble.data.config.JpaVendorAdapterConfig;
import com.blazartech.scrabble.data.config.TransactionManagerConfig;
import com.blazartech.scrabble.data.entity.GameEntity;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 *
 * @author scott
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
    GameEntityRepositoryTest.GameEntityRepositoryTestConfiguration.class,
    TestEntityManagerConfiguration.class,
    TestDataSourceConfiguration.class,
    JpaVendorAdapterConfig.class,
    TransactionManagerConfig.class
})
@Transactional
@Slf4j
public class GameEntityRepositoryTest {
    
    @Configuration
    public static class GameEntityRepositoryTestConfiguration {
        
    }
    
    @Autowired
    private GameEntityRepository instance;
    
    public GameEntityRepositoryTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        
        GameEntity g1 = new GameEntity();
        g1.setStsCde(GameStatus.Playing);
        
        GameEntity g2 = new GameEntity();
        g2.setStsCde(GameStatus.Playing);
        
        GameEntity g3 = new GameEntity();
        g3.setStsCde(GameStatus.Complete);
        
        instance.saveAll(List.of(g1, g2, g3));
    }
    
    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testFindByStsCde() {
        log.info("findByStsCde");
        
        // get all games.
        List<GameEntity> allGames = instance.findAll();
        assertEquals(3, allGames.size());
        
        // get the playing
        List<GameEntity> playingGames = instance.findByStsCde(GameStatus.Playing);
        assertEquals(2, playingGames.size());
    }
    
}
