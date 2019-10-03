package com.example.pssa.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String GET_ALL_PRODUCTS_QUEUE = "getAllProductsQueue";
    public static final String GET_PRODUCT_BY_ID_QUEUE = "getProductByIdQueue";
    public static final String ADD_PRODUCT_QUEUE = "addProductQueue";
    public static final String SEARCH_PRODUCTS_QUEUE = "searchProductsQueue";

    public static final String QUEUE_RESPONSES = "responses-queue";

    public static final String EXCHANGE_REQUEST = "request-exchange";

    @Bean
    Queue getAllProductsQueue() {
        return new Queue(GET_ALL_PRODUCTS_QUEUE);
    }

    @Bean
    Queue getProductByIdQueue() {
        return new Queue(GET_PRODUCT_BY_ID_QUEUE);
    }

    @Bean
    Queue addProductQueue() {
        return new Queue(ADD_PRODUCT_QUEUE);
    }

    @Bean
    Queue searchProductsQueue() {
        return new Queue(SEARCH_PRODUCTS_QUEUE);
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
    Binding binding1(DirectExchange requestExchange, Queue getAllProductsQueue) {
        return BindingBuilder.bind(getAllProductsQueue).to(requestExchange).with(GET_ALL_PRODUCTS_QUEUE);
    }

    @Bean
    Binding binding2(DirectExchange requestExchange, Queue getProductByIdQueue) {
        return BindingBuilder.bind(getProductByIdQueue).to(requestExchange).with(GET_PRODUCT_BY_ID_QUEUE);
    }

    @Bean
    Binding binding3(DirectExchange requestExchange, Queue addProductQueue) {
        return BindingBuilder.bind(addProductQueue).to(requestExchange).with(ADD_PRODUCT_QUEUE);
    }

    @Bean
    Binding binding4(DirectExchange requestExchange, Queue searchProductsQueue) {
        return BindingBuilder.bind(searchProductsQueue).to(requestExchange).with(SEARCH_PRODUCTS_QUEUE);
    }
}
