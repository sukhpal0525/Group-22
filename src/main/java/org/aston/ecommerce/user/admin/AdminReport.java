package org.aston.ecommerce.user.admin;

import org.aston.ecommerce.order.Order;
import org.aston.ecommerce.order.OrderRepository;
import org.aston.ecommerce.order.OrderService;
import org.aston.ecommerce.product.Product;
import org.aston.ecommerce.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AdminReport {

    @Autowired private OrderRepository orderRepository;
    @Autowired private ProductService productService;
    @Autowired private OrderService orderService;

    @GetMapping("/admin/report")
    public String displayReport(Model model) {
        List<Order> findAllOrders = orderRepository.findAll();
        model.addAttribute("findAllOrders", findAllOrders);
        List<Order> unprocessedOrders = orderService.findUnprocessedOrders();
        model.addAttribute("unprocessedOrders", unprocessedOrders);
        List<Product> lowStockProducts = productService.findAmberProducts();
        model.addAttribute("lowStockProducts", lowStockProducts);
        List<Product> redProducts = productService.findRedProducts();
        model.addAttribute("redProducts", redProducts);
        return "admin_report";
    }

}
