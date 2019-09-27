package com.example.pssa.interceptors;

import com.example.pssa.exceptions.WebServiceLoggingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.server.EndpointInterceptor;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class GlobalLogInterceptor implements EndpointInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(GlobalLogInterceptor.class);

    @Override
    public boolean handleRequest(MessageContext messageContext, Object endpoint) throws WebServiceLoggingException {

        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            messageContext.getRequest().writeTo(buffer);
            String payload = buffer.toString(StandardCharsets.UTF_8.name())
                    .replaceAll("<!--.*-->", "") // removing comments
                    .replaceAll(">\\s+<", "><"); // removing extra spaces, tabs etc

            logger.debug("----SOAP REQUEST----");
            logger.debug(payload);
        }
        catch (IOException ex) {
            throw new WebServiceLoggingException("Can't log the SOAP Request", ex);
        }

        return true;
    }

    @Override
    public boolean handleResponse(MessageContext messageContext, Object endpoint) throws WebServiceLoggingException {

        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            messageContext.getResponse().writeTo(buffer);
            String payload = buffer.toString(StandardCharsets.UTF_8.name());

            logger.debug("----SOAP RESPONSE----");
            logger.debug(payload);
        }
        catch (IOException ex) {
            throw new WebServiceLoggingException("Can't log the SOAP Response", ex);
        }

        return true;
    }

    @Override
    public boolean handleFault(MessageContext messageContext, Object endpoint) throws WebServiceLoggingException {

        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            messageContext.getResponse().writeTo(buffer);
            String payload = buffer.toString(java.nio.charset.StandardCharsets.UTF_8.name());

            logger.debug("---- SOAP RESPONSE ( FAULT ) ----");
            logger.debug(payload);
        }
        catch (IOException ex) {
            throw new WebServiceLoggingException("Can't log the SOAP Fault", ex);
        }

        return true;
    }

    @Override
    public void afterCompletion(MessageContext messageContext, Object endpoint, Exception ex) throws Exception {

    }
}
