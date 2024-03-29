package com.example.pssa.controllers;

import com.example.pssa.exceptions.ServiceUnavailableException;
import com.example.pssa.feignclients.ProductClient;
import feign.FeignException;
import org.example.soap.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;

@Endpoint
public class ProductControllerSOAP {

    private static final String NAMESPACE_URI = "http://example.org/soap";
    private static final Logger logger = LoggerFactory.getLogger(ProductControllerSOAP.class);

    @Autowired
    private ProductClient productClient;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetAllProductsRequest")
    @ResponsePayload
    public GetAllProductsResponse getAllProducts(@RequestPayload GetAllProductsRequest request) {

        logger.debug("SOAP adapter's " + (new Object() {}.getClass().getEnclosingMethod().getName()) + " method was called");

        GetAllProductsResponse response = new GetAllProductsResponse();
        try {
            List<ProductInfo> products = productClient.getAllProducts();
            response.getProducts().addAll(products);
        } catch (FeignException ex) {
            throw new ServiceUnavailableException("Service unavailable", ex);
        }

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetProductByIdRequest")
    @ResponsePayload
    public GetProductByIdResponse getProductById(@RequestPayload GetProductByIdRequest request) {

        logger.debug("SOAP adapter's " + (new Object() {}.getClass().getEnclosingMethod().getName()) + " method was called");

        GetProductByIdResponse response = new GetProductByIdResponse();
        try {
            ProductInfo product = productClient.getProductById(request.getId());
            response.setProduct(product);
        } catch (FeignException ex) {
            throw new ServiceUnavailableException("Service unavailable", ex);
        }

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "AddProductRequest")
    @ResponsePayload
    public AddProductResponse addProduct(@RequestPayload AddProductRequest request) {

        logger.debug("SOAP adapter's " + (new Object() {}.getClass().getEnclosingMethod().getName()) + " method was called");

        AddProductResponse response = new AddProductResponse();
        try {
            ProductInfo receivedProduct =  request.getProduct();
            receivedProduct.setId(null);
            ProductInfo addedProduct = productClient.addProduct(receivedProduct);
            response.setProduct(addedProduct);
        } catch (FeignException ex) {
            throw new ServiceUnavailableException("Service unavailable", ex);
        }

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "SearchRequest")
    @ResponsePayload
    public SearchResponse searchProducts(@RequestPayload SearchRequest request) {

        logger.debug("SOAP adapter's " + (new Object() {}.getClass().getEnclosingMethod().getName()) + " method was called");

        SearchResponse response = new SearchResponse();
        try {
            List<String> names = productClient.searchProducts(request.getName(), request.getParameter(), request.getValue());
            response.getProductNames().addAll(names);
        } catch (FeignException ex) {
            throw new ServiceUnavailableException("Service unavailable", ex);
        }

        return response;
    }
}
