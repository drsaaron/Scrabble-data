/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blazartech.scrabble.data.entity.repos;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 *
 * @author scott
 */
@Configuration
@PropertySource("classpath:unittest.properties")
public class TestDataSourceConfiguration {

    @Value("${test.db.user}")
    private String user;

    @Value("${test.db.driverClass}")
    private String driverClass;

    @Value("${test.db.url}")
    private String url;

    @Bean
    public DataSource quoteOfTheDayDataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName(driverClass);
        ds.setUrl(url);
        ds.setUsername(user);
        ds.setPassword("blah");

        return ds;
    }
}
