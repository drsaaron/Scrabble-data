/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.blazartech.scrabble.mq.listener;

import com.blazartech.scrabble.data.app.GamePlayerRound;
import com.blazartech.scrabble.data.process.GamePlayerRoundAddedHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import static org.springframework.amqp.support.AmqpHeaders.DELIVERY_TAG;
import static org.springframework.amqp.support.AmqpHeaders.RECEIVED_ROUTING_KEY;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

/**
 *
 * @author scott
 */
@Component
@Slf4j
public class GamePlayerRoundAddedMessageListener {

    @Autowired
    private GamePlayerRoundAddedHandler handler;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @RabbitListener(queues = "scrabble-gamePlayerRound")
    public void onMessage(String json, Channel channel, @Header(DELIVERY_TAG) long deliveryTag, @Header(RECEIVED_ROUTING_KEY) String topic) throws JsonProcessingException, IOException {
        log.info("json to process = {} on topic {}", json, topic);

        try {
            GamePlayerRound item = objectMapper.readValue(json, GamePlayerRound.class);
            log.info("got item {}", item);

            handler.handleGamePlayerRoundAdded(item);
        } catch (Exception e) {
            log.error("got exception processing message: " + e.getMessage(), e);
            channel.basicReject(deliveryTag, false);
        }
    }
}
