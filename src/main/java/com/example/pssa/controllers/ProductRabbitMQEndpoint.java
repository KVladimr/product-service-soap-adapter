package com.example.pssa.controllers;

import com.example.pssa.rabbitmq.RabbitMessageSender;
import com.example.pssa.utils.JsonUtils;
import org.example.rabbit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Endpoint
public class ProductRabbitMQEndpoint {

    private static final String NAMESPACE_URI = "http://example.org/rabbit";
    private static final Logger logger = LoggerFactory.getLogger(ProductRabbitMQEndpoint.class);

    @Autowired
    private RabbitMessageSender sender;


    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetAllProductsRequest")
    @ResponsePayload
    public GetAllProductsResponse getAllProducts(@RequestPayload GetAllProductsRequest request) throws IOException {

        logger.debug("RabbitMQ Endpoint's " + (new Object() {}.getClass().getEnclosingMethod().getName()) + " method was called");

        GetAllProductsResponse response = new GetAllProductsResponse();
        List<ProductInfo> products = sender.sendGetAllProductsRequest("Message from rabbit endpoint");
        response.getProducts().addAll(products);

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetProductByIdRequest")
    @ResponsePayload
    public GetProductByIdResponse getProductById(@RequestPayload GetProductByIdRequest request) throws IOException {

        logger.debug("RabbitMQ Endpoint's " + (new Object() {}.getClass().getEnclosingMethod().getName()) + " method was called");

        GetProductByIdResponse response = new GetProductByIdResponse();
        ProductInfo product = sender.sendGetProductByIdRequest(request.getId());
        response.setProduct(product);

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "AddProductRequest")
    @ResponsePayload
    public AddProductResponse addProduct(@RequestPayload AddProductRequest request) throws IOException {

        logger.debug("RabbitMQ Endpoint's " + (new Object() {}.getClass().getEnclosingMethod().getName()) + " method was called");

        AddProductResponse response = new AddProductResponse();
        ProductInfo product = sender.sendAddProductRequest(JsonUtils.asJsonString(request.getProduct()));
        response.setProduct(product);

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "SearchRequest")
    @ResponsePayload
    public SearchResponse searchProducts(@RequestPayload SearchRequest request) throws IOException {

        logger.debug("RabbitMQ Endpoint's " + (new Object() {}.getClass().getEnclosingMethod().getName()) + " method was called");

        SearchResponse response = new SearchResponse();

        Map<String, String> queryParameters = new HashMap<>();
        queryParameters.put("name", request.getName());
        queryParameters.put("parameter", request.getParameter());
        queryParameters.put("value", request.getValue());

        List<String> names = sender.sendSearchProductsRequest(JsonUtils.asJsonString(queryParameters));
        response.getProductNames().addAll(names);

        return response;
    }
}
