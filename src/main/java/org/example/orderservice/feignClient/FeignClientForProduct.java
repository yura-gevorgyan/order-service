package org.example.orderservice.feignClient;

import org.example.orderservice.entity.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "product-service", url = "${product.service.url}")
public interface FeignClientForProduct {

    @GetMapping("/products/item/{id}")
    Product getProduct(@PathVariable("id") String id);

    @PutMapping("/products/{id}")
    Product updateProduct(@PathVariable("id") String id, @RequestBody Product product);
}
