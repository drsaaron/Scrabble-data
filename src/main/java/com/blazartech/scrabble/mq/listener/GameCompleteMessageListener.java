/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.blazartech.scrabble.mq.listener;

import com.blazartech.scrabble.data.app.Game;
import com.blazartech.scrabble.data.process.GameCompleteHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.rabbitmq.client.Channel;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@Profile("!build")
public class GameCompleteMessageListener implements ScrabbleMessageListener<Game> {

    @Autowired
    private GameCompleteHandler handler;

    @RabbitListener(queues = "${scrabble.mq.rabbit.gamecompleted.queueName}", concurrency = "3", messageConverter = "jsonMessageConverter")
    @Override
    public void onMessage(Game item, Channel channel, @Header(DELIVERY_TAG) long deliveryTag, @Header(RECEIVED_ROUTING_KEY) String topic) throws JsonProcessingException, IOException {
        log.info("json to process = {} on topic {}", item, topic);

        try {
            handler.handleGameComplete(item);
        } catch (Exception e) {
            log.error("got exception processing message: " + e.getMessage(), e);
            channel.basicReject(deliveryTag, false);
        }
    }
}
