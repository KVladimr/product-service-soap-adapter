package com.example.pssa.feignclients;

import com.example.pssa.exceptions.ServiceFaultException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.apache.commons.io.IOUtils;
import org.example.soap.ServiceStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Reader;

@Component
public class FeignErrorDecoder implements ErrorDecoder {

    //private final ErrorDecoder errorDecoder = new Default();

    //private static final Logger logger = LoggerFactory.getLogger(FeignErrorDecoder.class);

    @Override
    public Exception decode(String s, Response response) {

        String message = null;
        Reader reader = null;

        try {
            reader = response.body().asReader();
            String result = IOUtils.toString(reader);
            ObjectMapper mapper = new ObjectMapper();
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

            ExceptionMessage exceptionMessage = mapper.readValue(result, ExceptionMessage.class);

            message = exceptionMessage.getMessage();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        ServiceStatus serviceStatus = new ServiceStatus();
        serviceStatus.setMessage(message == null ? "Not categorised REST exception" : message);
        serviceStatus.setStatusCode(HttpStatus.valueOf(response.status()).toString());

        return new ServiceFaultException("Exception returned from REST service.", serviceStatus);


    }
}
