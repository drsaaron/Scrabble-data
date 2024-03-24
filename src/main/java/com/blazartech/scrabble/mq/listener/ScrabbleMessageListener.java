/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.blazartech.scrabble.mq.listener;

import com.blazartech.scrabble.data.app.ScrabbleData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.rabbitmq.client.Channel;
import java.io.IOException;
import static org.springframework.amqp.support.AmqpHeaders.DELIVERY_TAG;
import static org.springframework.amqp.support.AmqpHeaders.RECEIVED_ROUTING_KEY;
import org.springframework.messaging.handler.annotation.Header;

/**
 * interface for all message listeners in the scrabble application
 * @author scott
 * @param <T>
 */
public interface ScrabbleMessageListener<T extends ScrabbleData> {
    
    public void onMessage(T item, Channel channel, @Header(DELIVERY_TAG) long deliveryTag, @Header(RECEIVED_ROUTING_KEY) String topic) throws JsonProcessingException, IOException;
}
