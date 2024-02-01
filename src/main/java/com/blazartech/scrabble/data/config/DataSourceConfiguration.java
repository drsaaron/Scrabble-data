/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.blazartech.scrabble.data.config;

import com.blazartech.products.crypto.BlazarCryptoFile;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author scott
 */
@Configuration
public class DataSourceConfiguration {
    
    @Value("${scrabble.db.userID}")
    private String userID;
    
    @Value("${scrabble.db.resourceID}")
    private String resourceID;
    
    @Value("${scrabble.db.driverClass}")
    private String driverClass;
    
    @Value("${scrabble.db.url}")
    private String url;
    
    @Autowired
    private BlazarCryptoFile cryptoFile;
    
    @Bean(destroyMethod = "")
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(userID);
        config.setPassword(cryptoFile.getPassword(userID, resourceID));
        config.setDriverClassName(driverClass);
        config.setMaximumPoolSize(5);
        config.setPoolName("scrabble-pool");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        
        DataSource ds = new HikariDataSource(config);
        
        return ds;
    }
}
