package org.aston.ecommerce.order;

import java.util.List;

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

@Slf4j
@Service
public class OrderService {

    @Autowired private OrderRepository orderRepository;
    @Autowired private ProductRepository productRepository;
    @Autowired private ProductService productService;

    public List<Order> getOrdersByCustomerId(Long customerId) {
        return orderRepository.findAllByCustomerId(customerId);
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
}