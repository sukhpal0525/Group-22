package org.aston.ecommerce.user.admin;


import lombok.extern.slf4j.Slf4j;
import org.aston.ecommerce.order.OrderRepository;
import org.aston.ecommerce.order.Order;
import org.aston.ecommerce.order.OrderService;
import org.aston.ecommerce.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
@Slf4j
@Service
@Controller
public class AdminOrders {

    @Autowired private OrderService orderService;
    @Autowired private OrderRepository orderRepository;
    @Autowired private UserRepository userRepository;

    @GetMapping("/admin/all-orders")
    public String returnAllOrders(Model model) {
        List<Order> allOrders = orderRepository.findAll();
        log.debug("Number of orders: {}", allOrders.size());
        model.addAttribute("allOrders", allOrders);
        return "admin_orders";
    }

}
