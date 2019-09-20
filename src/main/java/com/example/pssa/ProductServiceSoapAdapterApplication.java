package com.example.pssa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ProductServiceSoapAdapterApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductServiceSoapAdapterApplication.class, args);
    }

}
