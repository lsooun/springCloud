package com.how2java.springcloud.controller;

import com.how2java.springcloud.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {
    @Autowired
    ProductService productService;

    @GetMapping(value = "/products", produces = { "application/json;charset=UTF-8" })
    public Object products() {
        return productService.listProduct();
    }
}
