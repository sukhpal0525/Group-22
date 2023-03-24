package org.aston.ecommerce.user.admin;

import org.aston.ecommerce.order.Order;
import org.aston.ecommerce.order.OrderRepository;
import org.aston.ecommerce.order.OrderService;
import org.aston.ecommerce.product.Product;
import org.aston.ecommerce.user.User;
import org.aston.ecommerce.user.UserRepository;
import org.aston.ecommerce.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired private OrderService orderService;
    @Autowired private UserService userService;
    @Autowired private OrderRepository orderRepository;

    @GetMapping("/dashboard")
    public String viewDashboard(Model model) {
        return "admin_dashboard";
    }

    @GetMapping("/sales")
    public String showSales(Model model) {
        model.addAttribute("totalOrders", orderService.getTotalOrders());
        model.addAttribute("totalRevenue", orderService.getTotalRevenue());
        model.addAttribute("totalProfit", orderService.getTotalProfit());
        return "sales";
    }

    @GetMapping("/user/{id}")
    public String viewUserOrders(@PathVariable("id") Long id, Model model) {
        User user = userService.getUserById(id);
        List<Order> orders = orderRepository.findAllByCustomer(user);

        int totalOrders = orders.size();
        int totalProductsPurchased = userService.getTotalProductCount(user);
        double totalAmountSpent = userService.getTotalAmountSpent(user);
        List<Product> products = userService.getProductsByUser(user);
        List<Order.OrderItem> orderItems = new ArrayList<>();
        for (Order order : orders) {
            orderItems.addAll(order.getOrderItems());
        }

        model.addAttribute("user", user);
        model.addAttribute("orders", orders);
        model.addAttribute("orderItems", orderItems);
        model.addAttribute("totalOrders", totalOrders);
        model.addAttribute("totalProductsPurchased", totalProductsPurchased);
        model.addAttribute("totalAmountSpent", totalAmountSpent);
        model.addAttribute("products", products);

        return "user_orders";
    }

}
