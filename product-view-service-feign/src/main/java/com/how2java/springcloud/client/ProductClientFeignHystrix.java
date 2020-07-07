package com.how2java.springcloud.client;

import com.how2java.springcloud.pojo.Product;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductClientFeignHystrix implements ProductClientFeign {
    @Override
    public List<Product> listProduct() {
        List<Product> ps = new ArrayList<>();
        ps.add(new Product(0, "产品数据服务不可用", 0));

        return ps;
    }
}
