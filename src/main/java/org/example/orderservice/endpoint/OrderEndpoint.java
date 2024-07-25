package org.example.orderservice.endpoint;

import lombok.RequiredArgsConstructor;
import org.example.orderservice.entity.Order;
import org.example.orderservice.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderEndpoint {

    private final OrderService orderService;

    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    @CrossOrigin("*")
    private ResponseEntity<Order> saveOrder(@RequestBody Order order,
                                            @PathVariable String id) {

        return ResponseEntity.ok(orderService.save(order, id));

    }

    @GetMapping("/{index}")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin("*")
    private ResponseEntity<Page<Order>> getAllOrdersByUser(@PathVariable String index, @RequestParam("id") int id) {
        return ResponseEntity.ok(orderService.getAll(index, id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin("*")
    private ResponseEntity<Order> deleteOrderByProduct(@PathVariable int id) {
        orderService.deleteOrderByProductId(id);
        return ResponseEntity.ok().build();
    }
}
