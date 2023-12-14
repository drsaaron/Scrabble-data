/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.blazartech.scrabble.mq.config;

import com.blazartech.products.crypto.BlazarCryptoFile;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.connection.RabbitConnectionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author scott
 */
@Configuration
public class RabbitConfiguration {

    @Bean
    public FanoutExchange fanout() {
        return new FanoutExchange("scrabble");
    }

    @Value("${scrabble.mq.rabbit.userID}")
    private String rabbitUserID;
    
    @Value("${scrabble.mq.rabbit.resourceID}")
    private String rabbitResourceID;
    
    @Value("${scrabble.mq.rabbit.host}")
    private String rabbitHost;
    
    @Value("${scrabble.mq.rabbit.port}")
    private int rabbitPort;
    
    @Autowired
    private BlazarCryptoFile cryptoFile;
    
    @Bean
    public RabbitConnectionFactoryBean builConnectionFactory() {
        RabbitConnectionFactoryBean factory = new RabbitConnectionFactoryBean();

        // Generic connection properties
        factory.setHost(rabbitHost);
        factory.setPort(rabbitPort);
        factory.setUsername(rabbitUserID);
        factory.setPassword(cryptoFile.getPassword(rabbitUserID, rabbitResourceID));


        return factory;
    }

}
