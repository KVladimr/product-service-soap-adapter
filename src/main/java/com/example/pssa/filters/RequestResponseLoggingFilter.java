package com.example.pssa.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Component
@Order(1)
public class RequestResponseLoggingFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(RequestResponseLoggingFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        try {
            filterChain.doFilter(requestWrapper, responseWrapper);
        } finally {

            logger.debug("------ SOAP REQUEST ------");
            logger.trace("Request headers: {}", buildHeadersMap(request));
            logger.debug("Request URI: {}", request.getRequestURI());
            logger.debug("Http Method: {}", request.getMethod());

            String requestBody = new String(requestWrapper.getContentAsByteArray())
                    .replaceAll("<!--.*-->", "") // removing comments
                    .replaceAll(">\\s+<", "><"); // removing extra spaces, tabs etc
            logger.trace("Request body: {}", requestBody);

            logger.debug("------ SOAP RESPONSE ------");
            logger.trace("Response headers: {}", buildHeadersMap(response));
            logger.debug("Response status: {}", response.getStatus());

            String responseBody = new String(responseWrapper.getContentAsByteArray());
            logger.info("Response body: {}", responseBody);

            responseWrapper.copyBodyToResponse();
        }
    }

    private Map<String, String> buildHeadersMap(HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();

        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }

        return map;
    }

    private Map<String, String> buildHeadersMap(HttpServletResponse response) {
        Map<String, String> map = new HashMap<>();

        Collection<String> headerNames = response.getHeaderNames();
        for (String header : headerNames) {
            map.put(header, response.getHeader(header));
        }

        return map;
    }
}
