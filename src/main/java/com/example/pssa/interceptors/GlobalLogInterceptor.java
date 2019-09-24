package com.example.pssa.interceptors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.server.EndpointInterceptor;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

@Component
public class GlobalLogInterceptor implements EndpointInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(GlobalLogInterceptor.class);

    @Override
    public boolean handleRequest(MessageContext messageContext, Object endpoint) throws Exception {

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        messageContext.getRequest().writeTo(buffer);
        String payload = buffer.toString(StandardCharsets.UTF_8.name());

        logger.info("----SOAP REQUEST----");
        logger.info(payload);

        return true;
    }

    @Override
    public boolean handleResponse(MessageContext messageContext, Object endpoint) throws Exception {

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        messageContext.getResponse().writeTo(buffer);
        String payload = buffer.toString(StandardCharsets.UTF_8.name());

        logger.info("----SOAP RESPONSE----");
        logger.info(payload);

        return true;
    }

    @Override
    public boolean handleFault(MessageContext messageContext, Object endpoint) throws Exception {
        return true;
    }

    @Override
    public void afterCompletion(MessageContext messageContext, Object endpoint, Exception ex) throws Exception {

    }
}
