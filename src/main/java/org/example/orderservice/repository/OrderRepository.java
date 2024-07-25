package org.example.orderservice.repository;

import org.example.orderservice.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    Page<Order> findAllByUserId(Pageable pageable, int id);

    void deleteAllByProductId(int id);
}
