package com.example.pssa.rabbitmq;

import com.example.pssa.config.RabbitConfig;
import com.example.pssa.exceptions.MyRabbitFailureException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.rabbit.ProductInfo;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RabbitMessageSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    // TODO: Modify error handling

    public List<ProductInfo> sendGetAllProductsRequest(String message) {

        MessageProperties messageProperties = new MessageProperties();
        messageProperties.getHeaders().put("service-method", "getAllProducts");
        Message amqpMassage = new Message(message.getBytes(), messageProperties);

        try {
            String stringResult = (String)rabbitTemplate.convertSendAndReceive(RabbitConfig.QUEUE_REQUESTS, amqpMassage);
            return objectMapper.readValue(stringResult, new TypeReference<List<ProductInfo>>(){});
        } catch (Exception e) {
            throw new MyRabbitFailureException("Exception received: " + e.getMessage());
        }
    }

    public ProductInfo sendGetProductByIdRequest(String id) {

        MessageProperties messageProperties = new MessageProperties();
        messageProperties.getHeaders().put("service-method", "getProductById");
        Message amqpMassage = new Message(id.getBytes(), messageProperties);

        try {
            String stringResult = (String)rabbitTemplate.convertSendAndReceive(RabbitConfig.QUEUE_REQUESTS, amqpMassage);
            return objectMapper.readValue(stringResult, ProductInfo.class);
        } catch (Exception e) {
            throw new MyRabbitFailureException("Exception received: " + e.getMessage());
        }
    }

    public ProductInfo sendAddProductRequest(String jsonProductToAdd) {

        MessageProperties messageProperties = new MessageProperties();
        messageProperties.getHeaders().put("service-method", "addProduct");
        Message amqpMassage = new Message(jsonProductToAdd.getBytes(), messageProperties);

        try {
            String stringResult = (String)rabbitTemplate.convertSendAndReceive(RabbitConfig.QUEUE_REQUESTS, amqpMassage);
            return objectMapper.readValue(stringResult, ProductInfo.class);
        } catch (Exception e) {
            throw new MyRabbitFailureException("Exception received: " + e.getMessage());
        }
    }

    public List<String> sendSearchProductsRequest(String jsonParameterMap) {

        MessageProperties messageProperties = new MessageProperties();
        messageProperties.getHeaders().put("service-method", "searchProducts");
        Message amqpMassage = new Message(jsonParameterMap.getBytes(), messageProperties);

        try {
            String stringResult = (String)rabbitTemplate.convertSendAndReceive(RabbitConfig.QUEUE_REQUESTS, amqpMassage);
            return objectMapper.readValue(stringResult, new TypeReference<List<String>>(){});
        } catch (Exception e) {
            throw new MyRabbitFailureException("Exception received: " + e.getMessage());
        }
    }
}
