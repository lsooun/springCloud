package com.how2java.springcloud.service;

import com.how2java.springcloud.client.ProductClientRibbon;
import com.how2java.springcloud.pojo.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    ProductClientRibbon productClientRibbon;

    public List<Product> listProduct() {
        return productClientRibbon.listProduct();
    }
}
