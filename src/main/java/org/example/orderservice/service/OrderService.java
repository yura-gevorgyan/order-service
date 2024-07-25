package org.example.orderservice.service;

import org.example.orderservice.entity.Order;
import org.springframework.data.domain.Page;

import java.net.URI;

public interface OrderService {
    Order save(Order order, String id);

    Page<Order> getAll(String index,int id);

    void deleteOrderByProductId(int id);
}
