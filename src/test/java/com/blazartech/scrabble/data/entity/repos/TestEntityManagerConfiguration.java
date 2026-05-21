/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blazartech.scrabble.data.entity.repos;

import java.util.Properties;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

/**
 *
 * @author scott
 */
@Configuration
@EnableJpaRepositories(basePackages = "com.blazartech.scrabble.data.entity.repos",
        transactionManagerRef = "txManager",
        entityManagerFactoryRef = "entityManagerFactory"
)
public class TestEntityManagerConfiguration {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JpaVendorAdapter jpaVendorDapter;

    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean getEntityManagerFactory() {
        LocalContainerEntityManagerFactoryBean f = new LocalContainerEntityManagerFactoryBean();
        f.setDataSource(dataSource);
        f.setPersistenceXmlLocation("classpath:META-INF/test-persistence.xml");
        f.setJpaVendorAdapter(jpaVendorDapter);
        f.setPersistenceUnitName("test_com.blazartech_Scrabble-data_jar_1.0-SNAPSHOTPU");

        /* do not auto-create tables.  Because I've changed the GameEntity bean to use
           an enumeration property with a converter, auto-creating the table adds a check
           constraint on the StsCde.  That check constraint looks perfectly fine.  But
           inserting into the table fails becuase the constraint is violated.  AFter trying
           many things to fix the constraint or understand why it's failinbg, I give up
           and will just create the tables in an init script without the check constraint.
        */
        Properties props = new Properties();
        props.setProperty("hibernate.hbm2ddl.auto", "none");
        f.setJpaProperties(props);
        
        return f;
    }
}
