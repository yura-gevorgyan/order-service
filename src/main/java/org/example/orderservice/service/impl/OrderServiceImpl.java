package org.example.orderservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.orderservice.entity.Order;
import org.example.orderservice.entity.Product;
import org.example.orderservice.entity.enums.Status;
import org.example.orderservice.exception.NotProductException;
import org.example.orderservice.exception.NotSuchProduct;
import org.example.orderservice.feignClient.FeignClientForProduct;
import org.example.orderservice.repository.OrderRepository;
import org.example.orderservice.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final FeignClientForProduct productService;

    @Override
    public Order save(Order order, String id) {
        Product product = productService.getProduct(id);
        if (product != null) {
            if (order.getOrderQuantity() <= product.getCount()) {
                order.setOrderDate(LocalDateTime.now());
                order.setOrderPrice((product.getPrice() * order.getOrderQuantity()));
                order.setStatus(Status.NEW);
                order.setProductId(product.getId());
                product.setCount((product.getCount() - order.getOrderQuantity()));
                productService.updateProduct(String.valueOf(product.getId()), product);
                return orderRepository.save(order);
            } else {
                throw new NotSuchProduct();
            }
        } else {
            throw new NotProductException();
        }
    }

    @Override
    public Page<Order> getAll(String indexStr, int id) {
        try {
            int index = Integer.parseInt(indexStr);

            if (index <= 0) {
                return orderRepository.findAll(PageRequest.of(0, 8).withSort(Sort.by("id")));
            }
            Page<Order> products = orderRepository.findAllByUserId(PageRequest.of(index - 1, 8), id);

            if (products.getTotalPages() < index) {
                return orderRepository.findAllByUserId(PageRequest.of(0, 8), id);
            }

            return products;

        } catch (NumberFormatException e) {
            return orderRepository.findAllByUserId(PageRequest.of(0, 8), id);
        }
    }

    @Override
    @Transactional
    public void deleteOrderByProductId(int id) {
        orderRepository.deleteAllByProductId(id);
    }
}
