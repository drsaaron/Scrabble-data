/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.blazartech.scrabble.data.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author aar1069
 */
@Configuration
public class ObjectMapperConfiguration {
    
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper om = new ObjectMapper();
        om.findAndRegisterModules();
        return om;
    }
}
