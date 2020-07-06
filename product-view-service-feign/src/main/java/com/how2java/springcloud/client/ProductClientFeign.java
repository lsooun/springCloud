package com.how2java.springcloud.client;

import com.how2java.springcloud.pojo.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient("PRODUCT-DATA-SERVICE")
public interface ProductClientFeign {
    @GetMapping("/products")
    List<Product> listProduct();
}
