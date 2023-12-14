/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.blazartech.scrabble.mq.config;

import com.blazartech.products.crypto.BlazarCryptoFile;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.RabbitConnectionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 *
 * @author scott
 */
@Configuration
@Profile("!build")
public class RabbitConfiguration {
    
    @Value("${scrabble.mq.rabbit.exchangeName}")
    private String exchangeName;
    
    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(exchangeName, true, false);
    }
    
    private static final String DEAD_LETTER_EXCHANGE = "dead.letter.exchange";
    
    @Bean
    public DirectExchange backoutExchange() {
        return new DirectExchange(DEAD_LETTER_EXCHANGE, true, false);
    }
    
    @Value("${scrabble.mq.rabbit.gameplayerroundadded.queueName}")
    private String gamePlayerAddedQueueName;
    
    @Bean
    public Queue gamePlayerRoundAddedQueue() {
        Queue q = new Queue(gamePlayerAddedQueueName, true);
        q.addArgument("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE);
        q.addArgument("x-dead-letter-routing-key", gamePlayerAddedQueueName);
        return q;
    }
    
    @Bean 
    public Queue gamePlayerRoundAddedBackoutQueue() {
        Queue q = new Queue(gamePlayerAddedQueueName + ".bakout", true);
        return q;
    }
    
    @Value("${scrabble.mq.rabbit.gameplayerroundadded.topicName}")
    private String gamePlayerAddedTopicName;
    
    @Bean
    public Binding gamePlayerRoundAddedQueueBinding(TopicExchange exchange, Queue gamePlayerRoundAddedQueue) {
        return BindingBuilder.bind(gamePlayerRoundAddedQueue).to(exchange).with(gamePlayerAddedTopicName);
    }
    
    @Bean
    public Binding gamePlayerRoundAddedBackoutQueueBinding(DirectExchange backoutExchange, Queue gamePlayerRoundAddedBackoutQueue) {
        return BindingBuilder.bind(gamePlayerRoundAddedBackoutQueue).to(backoutExchange).with(gamePlayerAddedQueueName);
    }
    
    @Value("${scrabble.mq.rabbit.gamecompleted.queueName}")
    private String gameCompletedQueueName;
    
    @Bean
    public Queue gameCompletedQueue() {
        Queue q = new Queue(gameCompletedQueueName, true);
        q.addArgument("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE);
        q.addArgument("x-dead-letter-routing-key", gameCompletedQueueName);
        return q;
    }
    
    @Bean 
    public Queue gameCompletedBackoutQueue() {
        Queue q = new Queue(gameCompletedQueueName + ".bakout", true);
        return q;
    }
    
    @Value("${scrabble.mq.rabbit.gamecompleted.topicName}")
    private String gameCompletedTopicName;
    
    @Bean
    public Binding gameCompletedQueueBinding(TopicExchange exchange, Queue gameCompletedQueue) {
        return BindingBuilder.bind(gameCompletedQueue).to(exchange).with(gameCompletedTopicName);
    }
    
    @Bean
    public Binding gameCompletedBackoutQueueBinding(DirectExchange backoutExchange, Queue gameCompletedBackoutQueue) {
        return BindingBuilder.bind(gameCompletedBackoutQueue).to(backoutExchange).with(gameCompletedQueueName);
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
    public RabbitConnectionFactoryBean buildConnectionFactory() {
        RabbitConnectionFactoryBean factory = new RabbitConnectionFactoryBean();

        // Generic connection properties
        factory.setHost(rabbitHost);
        factory.setPort(rabbitPort);
        factory.setUsername(rabbitUserID);
        factory.setPassword(cryptoFile.getPassword(rabbitUserID, rabbitResourceID));

        return factory;
    }
    
  /*  @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(rabbitHost);
        connectionFactory.setPort(rabbitPort);
        connectionFactory.setUsername(rabbitUserID);
        connectionFactory.setPassword(cryptoFile.getPassword(rabbitUserID, rabbitResourceID));
        connectionFactory.setConnectionCacheSize(5);
        return connectionFactory;
    }
    
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate t = new RabbitTemplate(connectionFactory);
        return t;
    }*/

}
