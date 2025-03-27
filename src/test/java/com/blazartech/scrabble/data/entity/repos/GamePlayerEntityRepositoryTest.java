/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package com.blazartech.scrabble.data.entity.repos;

import com.blazartech.scrabble.data.config.JpaVendorAdapterConfig;
import com.blazartech.scrabble.data.config.TransactionManagerConfig;
import com.blazartech.scrabble.data.entity.GamePlayerEntity;
import jakarta.transaction.Transactional;
import java.util.Collection;
import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    GamePlayerEntityRepositoryTest.GamePlayerEntityRepositoryTestConfiguration.class,
    TestEntityManagerConfiguration.class,
    TestDataSourceConfiguration.class,
    JpaVendorAdapterConfig.class,
    TransactionManagerConfig.class
})
@Transactional
public class GamePlayerEntityRepositoryTest {
    
    private static final Logger log = LoggerFactory.getLogger(GamePlayerEntityRepositoryTest.class);
    
    @Configuration
    @PropertySource("classpath:unittest.properties")
    static class GamePlayerEntityRepositoryTestConfiguration {
        
    }
    
    @Autowired
    private GamePlayerEntityRepository instance;
    
    public GamePlayerEntityRepositoryTest() {
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
     * Test of findByGameId method, of class GamePlayerEntityRepository.
     */
    @Test
    @Sql("classpath:gamePlayerEntityTest.sql")
    public void testFindByGameId() {
        log.info("findByGameId");
        int gameId = 1;

        Collection<GamePlayerEntity> result = instance.findByGameId(gameId);
        assertEquals(2, result.size());
    }
    
}
