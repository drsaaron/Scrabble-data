/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.blazartech.scrabble.mq.listener;

import com.blazartech.scrabble.data.app.GamePlayerRound;
import com.blazartech.scrabble.data.process.GamePlayerRoundAddedHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.rabbitmq.client.Channel;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import static org.springframework.amqp.support.AmqpHeaders.DELIVERY_TAG;
import static org.springframework.amqp.support.AmqpHeaders.RECEIVED_ROUTING_KEY;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

/**
 *
 * @author scott
 */
@Component
@Profile("!build")
public class GamePlayerRoundAddedMessageListener implements ScrabbleMessageListener<GamePlayerRound> {

    private static final Logger log = LoggerFactory.getLogger(GamePlayerRoundAddedMessageListener.class);
    
    @Autowired
    private GamePlayerRoundAddedHandler handler;

    /** 
     * handle a game player round being added.  the process bean will update the 
     * game player round's rolling score.
     * 
     * @param item
     * @param channel
     * @param deliveryTag
     * @param topic
     * @throws JsonProcessingException
     * @throws IOException 
     */
    @RabbitListener(queues = "${scrabble.mq.rabbit.gameplayerroundadded.queueName}", concurrency = "4", messageConverter = "jsonMessageConverter", containerFactory = "rabbitListenerContainerFactory")
    @Override
    public void onMessage(GamePlayerRound item, Channel channel, @Header(DELIVERY_TAG) long deliveryTag, @Header(RECEIVED_ROUTING_KEY) String topic) throws JsonProcessingException, IOException {
        log.info("json to process = {} on topic {}", item, topic);

        try {
            handler.handleGamePlayerRoundAdded(item);
        } catch (Exception e) {
            log.error("got exception processing message: " + e.getMessage(), e);
            channel.basicReject(deliveryTag, false);
        }
    }
}
