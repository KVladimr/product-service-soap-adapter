package com.example.pssa.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String QUEUE_REQUESTS = "requests-queue";
    public static final String QUEUE_RESPONSES = "responses-queue";

    public static final String EXCHANGE_REQUEST = "request-exchange";

    @Bean
    Queue requestsQueue() {
        return new Queue(QUEUE_REQUESTS);
    }

    // Do we need it?
    @Bean
    Queue responsesQueue() {
        return new Queue(QUEUE_RESPONSES);
    }

    @Bean
    Exchange requestExchange() {
        return ExchangeBuilder.directExchange(EXCHANGE_REQUEST).build();
    }

    @Bean
    Binding binding(DirectExchange requestExchange, Queue requestsQueue) {
        return BindingBuilder.bind(requestsQueue).to(requestExchange).with(QUEUE_REQUESTS);
    }
}
