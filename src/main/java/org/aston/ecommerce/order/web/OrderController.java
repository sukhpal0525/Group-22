package org.aston.ecommerce.order.web;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.aston.ecommerce.order.Order;
import org.aston.ecommerce.order.OrderService;
import org.aston.ecommerce.user.CustomUserDetails;
import org.aston.ecommerce.user.User;
import org.aston.ecommerce.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/orders")
public class OrderController {

    @Autowired private OrderService orderService;
    @Autowired private UserRepository userRepository;

    @GetMapping("/{customerId}")
    public List<Order> getOrdersByCustomerId(
            @PathVariable Long customerId) {
        return orderService.getOrdersByCustomerId(customerId);
    }

    @GetMapping("/past_orders")
    public String getPastOrders(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof CustomUserDetails) {
            String username = ((CustomUserDetails) principal).getUsername();
            User user = userRepository.findByEmail(username);

            List<Order> orders = orderService.getOrdersByCustomerId(user.getId());
            model.addAttribute("orders", orders);
            model.addAttribute("empty", orders.isEmpty());
        } else {
            model.addAttribute("isNotLoggedIn", "yes");
        }
        return "orders_archive_display";
    }

    @GetMapping("/basket_order")
    public String viewBasket(Model model) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof CustomUserDetails) {
            String username = ((CustomUserDetails) principal).getUsername();
            User user = userRepository.findByEmail(username);
            //See if the currently logged-in user has an empty basket or not
            if (user.isBasketEmpty()) {
                model.addAttribute("empty", "yes");
            } else {
                Order order = orderService.createOrder(user);
                order = orderService.placeOrder(order);

                user.getBasket().getBasketItems().clear();
                user = userRepository.save(user);

                model.addAttribute("purchase_success", "yes");
                model.addAttribute("order", order);
                if (order.getRejectedItems().size() > 0) {
                    model.addAttribute("purchase_fail", "You tried to order more products than there is currently available in stock.");
                }
            }
        } else {
            model.addAttribute("isNotLoggedIn", "yes");
        }
        return "orders_display";
    }
}