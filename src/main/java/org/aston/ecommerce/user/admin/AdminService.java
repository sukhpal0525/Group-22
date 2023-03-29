package org.aston.ecommerce.user.admin;

import lombok.extern.slf4j.Slf4j;
import org.aston.ecommerce.order.Order;
import org.aston.ecommerce.order.OrderRepository;
import org.aston.ecommerce.order.OrderService;
import org.aston.ecommerce.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Slf4j
@Service
public class AdminService {

    @Autowired private OrderRepository orderRepository;
    @Autowired private ProductService productService;

    public double getProfit(LocalDateTime start, LocalDateTime end) {
        double markupMultiplier = productService.getMarkupMultiplier();
        double totalProfit = orderRepository.findAll().stream()
                .flatMap(order -> order.getOrderItems().stream())
                .mapToDouble(orderItem -> {
                    double itemCost = (orderItem.getProduct().getCost() != null ? orderItem.getProduct().getCost() : 0.0);
                    double markupAmount = (orderItem.getAmount() - (orderItem.getAmount() * markupMultiplier));
                    return (markupAmount - itemCost) * orderItem.getNumOfItems();
                })
                .sum();
        return totalProfit;
    }

    public double getDailyProfit() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfDay = now.truncatedTo(ChronoUnit.DAYS);
        return getProfit(startOfDay, now);
    }

    public double getWeeklyProfit() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfWeek = now.minusWeeks(1);
        return getProfit(startOfWeek, now);
    }

    public double getMonthlyProfit() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfMonth = now.minusMonths(1);
        return getProfit(startOfMonth, now);
    }

    public double getYearlyProfit() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfYear = now.minusYears(1);
        return getProfit(startOfYear, now);
    }
}
