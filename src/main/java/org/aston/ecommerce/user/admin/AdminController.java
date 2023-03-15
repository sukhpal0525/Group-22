package org.aston.ecommerce.user.admin;

import org.aston.ecommerce.order.OrderService;
import org.aston.ecommerce.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired private OrderService orderService;

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
}
