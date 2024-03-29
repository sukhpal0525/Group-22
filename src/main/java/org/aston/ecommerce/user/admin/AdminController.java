package org.aston.ecommerce.user.admin;

import lombok.extern.slf4j.Slf4j;
import org.aston.ecommerce.order.Order;
import org.aston.ecommerce.order.OrderRepository;
import org.aston.ecommerce.order.OrderService;
import org.aston.ecommerce.product.Product;
import org.aston.ecommerce.product.ProductRepository;
import org.aston.ecommerce.product.ProductService;
import org.aston.ecommerce.user.User;
import org.aston.ecommerce.user.UserRepository;
import org.aston.ecommerce.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired private OrderService orderService;
    @Autowired private UserService userService;
    @Autowired private UserRepository userRepository;
    @Autowired private OrderRepository orderRepository;
    @Autowired private AdminService adminService;
    @Autowired private ProductService productService;
    @Autowired private ProductRepository productRepository;

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

    @GetMapping("/analytics")
    public String getOrderData(Model model) {
        List<Order> orders = orderRepository.findAll();
        List<Product> products = productRepository.findAll();

        List<Product> returnProducts = this.productRepository.findAll()
                .stream()
                .sorted(Comparator.comparingInt(Product::getAmountAvailable))
                .limit(5)
                .collect(Collectors.toList());

        List<Map<String, Object>> productStock = new ArrayList<>();
        for (Product product : returnProducts) {
            Map<String, Object> productStockInfo = new HashMap<>();
            productStockInfo.put("name", product.getName());
            productStockInfo.put("stock", product.getAmountAvailable());
            productStock.add(productStockInfo);
        }


//        Map<User, List<Order>> customerOrders = new HashMap<>();
//        List<Order> topCustomers = this.orderRepository.findAllOrderByCustomerDesc();
//        for (Order order : topCustomers) {
//            customerOrders.put(order.getCustomer(), orderRepository.findAllByCustomer(order.getCustomer()));
//        }
//        model.addAttribute("topCustomers", customerOrders);

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime dayStartTime = now.with(LocalTime.MIN);
        LocalDateTime weekStartTime = LocalDate.now().with(WeekFields.of(Locale.ENGLISH).dayOfWeek(), 1).atStartOfDay();
        LocalDateTime monthStartTime = LocalDateTime.from(now.with(TemporalAdjusters.firstDayOfMonth()));
        LocalDateTime yearStartTime = LocalDateTime.from(now.with(TemporalAdjusters.firstDayOfYear()));

        List<Order> dayOrders = orderRepository.findByCreationDateAfter(dayStartTime);
        List<Order> weekOrders = orderRepository.findByCreationDateAfter(weekStartTime);
        List<Order> monthOrders = orderRepository.findByCreationDateAfter(monthStartTime);
        List<Order> yearOrders = orderRepository.findByCreationDateAfter(yearStartTime);

        double dailyProfit = adminService.getDailyProfit();
        double weeklyProfit = adminService.getWeeklyProfit();
        double monthlyProfit = adminService.getMonthlyProfit();
        double yearlyProfit = adminService.getYearlyProfit();
        double allTimeProfit = Double.parseDouble(orderService.getTotalProfit());

        model.addAttribute("productStock", productStock);
        model.addAttribute("dayOrders", dayOrders);
        model.addAttribute("dayProfit", dailyProfit);
        model.addAttribute("weekOrders", weekOrders);
        model.addAttribute("weekProfit", weeklyProfit);
        model.addAttribute("monthOrders", monthOrders);
        model.addAttribute("monthProfit", monthlyProfit);
        model.addAttribute("yearOrders", yearOrders);
        model.addAttribute("yearProfit", yearlyProfit);
        model.addAttribute("allOrders", orders);
        model.addAttribute("allTimeProfit", allTimeProfit);

        return "admin_analytics";
    }

    @GetMapping("/users")
    public String viewUsers(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "admin_users";
    }
}
