package com.example.pssa.rabbitmq;

import com.example.pssa.config.RabbitConfig;
import com.example.pssa.exceptions.MyRabbitFailureException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.rabbit.ProductInfo;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class RabbitMessageSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    // TODO: Modify error handling

    public List<ProductInfo> sendGetAllProductsRequest(String message) throws IOException {

        try {
            String stringResult = (String)rabbitTemplate.convertSendAndReceive(RabbitConfig.GET_ALL_PRODUCTS_QUEUE, message);
            return objectMapper.readValue(stringResult, new TypeReference<List<ProductInfo>>(){});
        } catch (Exception e) {
            throw new MyRabbitFailureException("Exception received: " + e.getMessage());
        }
    }

    public ProductInfo sendGetProductByIdRequest(String id) throws IOException {

        try {
            String stringResult = (String)rabbitTemplate.convertSendAndReceive(RabbitConfig.GET_PRODUCT_BY_ID_QUEUE, id);
            return objectMapper.readValue(stringResult, ProductInfo.class);
        } catch (Exception e) {
            throw new MyRabbitFailureException("Exception received: " + e.getMessage());
        }
    }

    public ProductInfo sendAddProductRequest(String jsonProductToAdd) throws IOException {

        try {
            String stringResult = (String)rabbitTemplate.convertSendAndReceive(RabbitConfig.ADD_PRODUCT_QUEUE, jsonProductToAdd);
            return objectMapper.readValue(stringResult, ProductInfo.class);
        } catch (Exception e) {
            throw new MyRabbitFailureException("Exception received: " + e.getMessage());
        }
    }

    public List<String> sendSearchProductsRequest(String jsonParameterMap) throws IOException {

        try {
            String stringResult = (String)rabbitTemplate.convertSendAndReceive(RabbitConfig.SEARCH_PRODUCTS_QUEUE, jsonParameterMap);
            return objectMapper.readValue(stringResult, new TypeReference<List<String>>(){});
        } catch (Exception e) {
            throw new MyRabbitFailureException("Exception received: " + e.getMessage());
        }
    }
}
