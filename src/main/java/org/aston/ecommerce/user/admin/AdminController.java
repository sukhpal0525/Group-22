package org.aston.ecommerce.user.admin;

import lombok.extern.slf4j.Slf4j;
import org.aston.ecommerce.order.Order;
import org.aston.ecommerce.order.OrderRepository;
import org.aston.ecommerce.order.OrderService;
import org.aston.ecommerce.product.Product;
import org.aston.ecommerce.product.ProductService;
import org.aston.ecommerce.user.User;
import org.aston.ecommerce.user.UserRepository;
import org.aston.ecommerce.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Slf4j
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired private OrderService orderService;
    @Autowired private UserService userService;
    @Autowired private UserRepository userRepository;
    @Autowired private OrderRepository orderRepository;
    @Autowired private ProductService productService;

    @GetMapping("/dashboard")
    public String viewDashboard(Model model) {

        //TODO: Sort This out
        model.addAttribute("products", this.productService.findAllProductsInAscendingOrder());
        model.addAttribute("amberProducts", this.productService.findAmberProducts());
        model.addAttribute("redProducts", this.productService.findRedProducts());
        model.addAttribute("unprocessedOrders", this.orderService.findUnprocessedFailedOrders());

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

    @GetMapping("/orders")
    public String getAllOrders(Model model) {
        List<Order> orders = this.orderRepository.findAll();

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime dayStartTime = now.with(LocalTime.MIN);
        LocalDateTime weekStartTime = LocalDate.now().with(WeekFields.of(Locale.ENGLISH).dayOfWeek(), 1).atStartOfDay();
        LocalDateTime monthStartTime = LocalDateTime.from(now.with(TemporalAdjusters.firstDayOfMonth()));
        LocalDateTime yearStartTime = LocalDateTime.from(now.with(TemporalAdjusters.firstDayOfYear()));

        List<Order> dayOrders = this.orderRepository.findByCreationDateAfter(dayStartTime);
        List<Order> weekOrders = this.orderRepository.findByCreationDateAfter(weekStartTime);
        List<Order> monthOrders = this.orderRepository.findByCreationDateAfter(monthStartTime);
        List<Order> yearOrders = this.orderRepository.findByCreationDateAfter(yearStartTime);

        model.addAttribute("dayOrders", dayOrders);
        model.addAttribute("weekOrders", weekOrders);
        model.addAttribute("monthOrders", monthOrders);
        model.addAttribute("yearOrders", yearOrders);
        model.addAttribute("allOrders", orders);

        return "order_data";
    }
}
