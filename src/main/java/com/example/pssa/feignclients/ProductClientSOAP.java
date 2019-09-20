package com.example.pssa.feignclients;

import org.example.soap.ProductInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "productsSOAP", url = "http://localhost:8080")
public interface ProductClientSOAP {
    @RequestMapping(method = RequestMethod.GET, value = "/product/")
    List<ProductInfo> getAllProducts();

    @RequestMapping(method = RequestMethod.POST, value = "/product/")
    ProductInfo addProduct(@RequestBody ProductInfo product);

    @RequestMapping(method = RequestMethod.GET, value = "/product/{id}")
    ProductInfo getProductById(@PathVariable("id") String id);

    @RequestMapping(method = RequestMethod.GET, value = "/product/search")
    List<String> searchProducts(@RequestParam(required=false) String name,
                                @RequestParam(required=false) String parameter,
                                @RequestParam(required=false) String value);
}
