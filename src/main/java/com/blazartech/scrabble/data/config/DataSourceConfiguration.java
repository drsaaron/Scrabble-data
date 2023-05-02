/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.blazartech.scrabble.data.config;

import com.blazartech.products.crypto.BlazarCryptoFile;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;
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
    
    @Bean
    public DataSource dataSource() {
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName(driverClass);
        ds.setUrl(url);
        ds.setUsername(userID);
        ds.setPassword(cryptoFile.getPassword(userID, resourceID));
        ds.setInitialSize(5);
        ds.setMaxTotal(5);
        
        return ds;
    }
}
