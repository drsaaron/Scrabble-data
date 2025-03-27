/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.blazartech.scrabble.mq.cap;

import com.blazartech.scrabble.data.app.ScrabbleData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 *
 * @author scott
 */
@Component
@Profile("!build")
public class EventSenderRabbitImpl implements EventSender {

    private static final Logger log = LoggerFactory.getLogger(EventSenderRabbitImpl.class);
    
    @Autowired
    private RabbitTemplate template;

    @Autowired
    private TopicExchange topicExchange;

    @Override
    public void sendEvent(String topicName, ScrabbleData object) {
        log.info("publishing message");
        template.convertAndSend(topicExchange.getName(), topicName, object);
    }

}
