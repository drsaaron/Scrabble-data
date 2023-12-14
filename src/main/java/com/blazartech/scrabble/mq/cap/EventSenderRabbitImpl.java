/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.blazartech.scrabble.mq.cap;

import com.blazartech.scrabble.data.app.ScrabbleData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import static org.springframework.amqp.core.ExchangeBuilder.fanoutExchange;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author scott
 */
@Component
@Slf4j
public class EventSenderRabbitImpl implements EventSender {

    @Autowired
    private RabbitTemplate template;

    @Autowired
    private TopicExchange topicExchange;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void sendEvent(String topicName, ScrabbleData object) {
        try {
            String objectJson = objectMapper.writeValueAsString(object);
            log.info("publishing message");
            template.convertAndSend(topicExchange.getName(), topicName, objectJson);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("error sending event: " + e.getMessage(), e);
        }
    }

}
