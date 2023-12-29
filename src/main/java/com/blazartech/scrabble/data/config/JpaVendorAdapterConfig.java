/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blazartech.scrabble.data.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

/**
 *
 * @author scott
 */
@Configuration
public class JpaVendorAdapterConfig {

    @Value("${scrabble.config.jpa.vendorType:MYSQL}")
    private String vendorType;

    @Value("${app.jpa.dialect}")
    private String dbDialect;

    @Bean
    public JpaVendorAdapter getJpaVendorAdapter() {
        HibernateJpaVendorAdapter va  = new HibernateJpaVendorAdapter();
        va.setShowSql(true);
        va.setDatabase(Database.valueOf(vendorType));
        va.setDatabasePlatform(dbDialect);
        va.setGenerateDdl(true);
        return va;
    }
}
