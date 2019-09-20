package com.example.pssa.controllers;

import com.example.pssa.feignclients.ProductClientSOAP;
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
    private ProductClientSOAP productClientSOAP;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetAllProductsRequest")
    @ResponsePayload
    public GetAllProductsResponse getAllProducts(@RequestPayload GetAllProductsRequest request) {

        logger.info("SOAP adapter's " + (new Object() {}.getClass().getEnclosingMethod().getName()) + " method was called");

        GetAllProductsResponse response = new GetAllProductsResponse();
        List<ProductInfo> products = productClientSOAP.getAllProducts();
        response.getProducts().addAll(products);

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetProductByIdRequest")
    @ResponsePayload
    public GetProductByIdResponse getProductById(@RequestPayload GetProductByIdRequest request) {

        logger.info("SOAP adapter's " + (new Object() {}.getClass().getEnclosingMethod().getName()) + " method was called");

        GetProductByIdResponse response = new GetProductByIdResponse();
        ProductInfo product = productClientSOAP.getProductById(request.getId());
        response.setProduct(product);

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "AddProductRequest")
    @ResponsePayload
    public AddProductResponse addProduct(@RequestPayload AddProductRequest request) {

        logger.info("SOAP adapter's " + (new Object() {}.getClass().getEnclosingMethod().getName()) + " method was called");

        AddProductResponse response = new AddProductResponse();
        ProductInfo receivedProduct =  request.getProduct();
        receivedProduct.setId(null);
        ProductInfo addedProduct = productClientSOAP.addProduct(receivedProduct);
        response.setProduct(addedProduct);

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "SearchRequest")
    @ResponsePayload
    public SearchResponse searchProducts(@RequestPayload SearchRequest request) {

        logger.info("SOAP adapter's " + (new Object() {}.getClass().getEnclosingMethod().getName()) + " method was called");

        SearchResponse response = new SearchResponse();
        List<String> names = productClientSOAP.searchProducts(request.getName(), request.getParameter(), request.getValue());
        response.getProductNames().addAll(names);
        return response;
    }
}
