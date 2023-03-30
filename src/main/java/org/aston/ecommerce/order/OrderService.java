package org.aston.ecommerce.order;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.aston.ecommerce.EcommerceApplication;
import org.aston.ecommerce.basket.Basket;
import org.aston.ecommerce.basket.BasketItem;
import org.aston.ecommerce.order.Order.OrderItem;
import org.aston.ecommerce.product.Product;
import org.aston.ecommerce.product.ProductRepository;
import org.aston.ecommerce.product.ProductService;
import org.aston.ecommerce.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.fasterxml.jackson.databind.type.LogicalType.Collection;

@Slf4j
@Service
public class OrderService {

    @Autowired private OrderRepository orderRepository;
    @Autowired private ProductRepository productRepository;
    @Autowired private ProductService productService;

    public List<Order> getOrdersByCustomerId(Long customerId) {
        List<Order> returnOrders = orderRepository.findAllByCustomerId(customerId);
        this.sortOrdersByStatusThenDate(returnOrders);
        return returnOrders;
    }

    public Order createOrder(User user) {
        Basket usersCurrentBasket = user.getBasket();
        Order order = new Order();
        order.setCustomer(user);
        order.setTransactionNumber(EcommerceApplication.generateString(16));
        for (BasketItem item : usersCurrentBasket.getBasketItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setNumOfItems(item.getAmount());
            orderItem.setProduct(item.getProduct());
            orderItem.setAmount(item.getAmount() * item.getProduct().getAmount());
            orderItem.setOrder(order);
            order.getOrderItems().add(orderItem);
        }
        return order;
    }

    public long getTotalOrders() {
        return orderRepository.count();
    }

    public double getTotalRevenue() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream().mapToDouble(Order::getOrderAmount).sum();
    }

    public String getTotalProfit() {
        double markupMultiplier = productService.getMarkupMultiplier();
        double totalProfit = orderRepository.findAll().stream()
                .flatMap(order -> order.getOrderItems().stream())
                .mapToDouble(orderItem -> {
                    double itemCost = (orderItem.getProduct().getCost() != null ? orderItem.getProduct().getCost() : 0.0);
                    double markupAmount = (orderItem.getAmount() - (orderItem.getAmount() * markupMultiplier));
                    return (markupAmount - itemCost) * orderItem.getNumOfItems();
                })
                .sum();
        return String.format("%.2f", totalProfit);
    }



    public double getTotalProfit(List<Order> orders) {
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

    public double calculateProfit(List<OrderItem> orderItems) {
        double markupMultiplier = productService.getMarkupMultiplier();
        double totalProfit = orderItems.stream()
                .mapToDouble(orderItem -> {
                    double itemCost = (orderItem.getProduct().getCost() != null ? orderItem.getProduct().getCost() : 0.0);
                    double markupAmount = (orderItem.getAmount() - (orderItem.getAmount() * markupMultiplier));
                    return (markupAmount - itemCost) * orderItem.getNumOfItems();
                })
                .sum();
        return totalProfit;
    }

    @Transactional
    public Order placeOrder(final Order order) {

        List<OrderItem> orderItems = order.getOrderItems();

        double orderAmount = orderItems.stream().map((item) -> {
            double amount = 0.0;
            Product product = productRepository.findById(item.getProduct().getId()).get();
            if (item.getNumOfItems() < product.getAmountAvailable()) {
                product.setAmountAvailable(product.getAmountAvailable() - item.getNumOfItems());
                product = this.productRepository.save(product);
                amount = product.getAmount() * item.getNumOfItems();
            } else {
                order.getRejectedItems().add(item);
            }
            return amount;
        })
                .mapToDouble(i -> i).sum();

        order.setOrderAmount(orderAmount);
        order.getOrderItems().removeAll(order.getRejectedItems());

        return orderRepository.save(order);
    }

    public List<Order> findAllIncomingAndOutgoingOrders(){
        List<Order> returnOrders = this.orderRepository.findAll()
                .stream()
                .filter(o -> o.getStatus() != Status.FAILED && o.getStatus() != Status.SUCCESS)
                .collect(Collectors.toList());
        this.sortOrdersByStatusThenDate(returnOrders);
        return returnOrders;
    }

    public List<Order> findOrdersByStatusAndDate(){
        List<Order> returnOrders = this.orderRepository.findAll();
        this.sortOrdersByStatusThenDate(returnOrders);
        return returnOrders;
    }

    public List<Order> findByStatus(String statusStr, Long customerId){
        Status status = Status.parseStatusStr(statusStr.toUpperCase());
        if(status != null){
            return this.findOrdersByStatusFilter(status, customerId);
        }
        return null;
    }

    public List<Order> findOrdersByStatusFilter(Status status, Long customerId){
        List<Order> returnOrders = this.getOrdersByCustomerId(customerId)
                .stream()
                .filter(o -> o.getStatus() == status)
                .collect(Collectors.toList());
        //DESCENDING
        Collections.sort(returnOrders, (Order o1, Order o2) -> o2.getOrderDate().compareTo(o1.getOrderDate()));
        return returnOrders;
    }

    public List<Order> findOrdersByStatusFilter(Status status){
        List<Order> returnOrders = this.orderRepository.findAll()
                .stream()
                .filter(o -> o.getStatus() == status)
                .collect(Collectors.toList());
        //DESCENDING
        Collections.sort(returnOrders, (Order o1, Order o2) -> o2.getOrderDate().compareTo(o1.getOrderDate()));
        return returnOrders;
    }

    //Sort all orders in DESCENDING order based on their date grouped by their enum status such that the most recent orders are shown first
    private void sortOrdersByStatusThenDate(List<Order> takeOrders){
        Collections.sort(takeOrders, new Comparator<Order>() {
            @Override
            public int compare(Order o1, Order o2) {
                if(o1.getStatus() == o2.getStatus()){
                    //DESCENDING
                    return o2.getOrderDate().compareTo(o1.getOrderDate());
                }else{
                    return o1.getStatus().compareTo(o2.getStatus());
                }
            }
        });
    }

    //Sort all orders in ASCENDING order based on when they were made such that the oldest unprocessed order is shown first
    private void sortOrdersByStatusThenDateDesc(List<Order> takeOrders){
        Collections.sort(takeOrders, new Comparator<Order>() {
            @Override
            public int compare(Order o1, Order o2) {
                if(o1.getStatus() == o2.getStatus()){
                    //ASCENDING
                    return o1.getOrderDate().compareTo(o2.getOrderDate());
                }else{
                    return o1.getStatus().compareTo(o2.getStatus());
                }
            }
        });
    }

    //Return all orders that are unprocessed or failed in DESCENDING order based on when they were made such that the oldest unprocessed order is shown first
    public List<Order> findUnprocessedFailedOrders(){
        List<Order> returnOrders = this.orderRepository.findAll()
                .stream()
                .filter(o -> o.getStatus() == Status.UNPROCESSED || o.getStatus() == Status.FAILED)
                .collect(Collectors.toList());
        //DESCENDING, just ensure that unprocessed comes first
        this.sortOrdersByStatusThenDateDesc(returnOrders);
        return returnOrders;
    }

    public List<Order> findUnprocessedOrders(){
        List<Order> returnOrders = this.orderRepository.findAll()
                .stream()
                .filter(o -> o.getStatus() == Status.UNPROCESSED)
                .collect(Collectors.toList());
        this.sortOrdersByStatusThenDateDesc(returnOrders);
        return returnOrders;
    }
}