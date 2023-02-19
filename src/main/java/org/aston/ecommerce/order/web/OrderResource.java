package org.aston.ecommerce.order.web;

import java.util.List;
import org.aston.ecommerce.order.Order;
import org.aston.ecommerce.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderResource {

    @Autowired
    private OrderService orderService;

    @GetMapping("/{customerId}")
    public List<Order> getOrdersByCustomerId(@PathVariable Long customerId) {
        return orderService.getOrdersByCustomerId(customerId);
    }

    @PostMapping
    public void placeOrder(@RequestBody Order order) {
        orderService.placeOrder(order);
    }
}