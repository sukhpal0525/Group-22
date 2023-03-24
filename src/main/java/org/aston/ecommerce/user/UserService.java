package org.aston.ecommerce.user;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.aston.ecommerce.order.Order;
import org.aston.ecommerce.order.OrderRepository;
import org.aston.ecommerce.product.Product;
import org.aston.ecommerce.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {

    @Autowired private UserRepository userRepository;
    @Autowired private OrderRepository orderRepository;
    @Autowired private ProductRepository productRepository;

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public List<Order> getOrdersByUser(User user) {
        return orderRepository.findAllByCustomer(user);
    }

    public int getTotalOrderCount(User user) {
        List<Order> orders = orderRepository.findAllByCustomer(user);
        return orders.size();
    }

    public int getTotalProductCount(User user) {
        List<Order> orders = orderRepository.findAllByCustomer(user);
        int totalCount = 0;
        for (Order order : orders) {
            for (Order.OrderItem orderItem : order.getOrderItems()) {
                totalCount += orderItem.getNumOfItems();
            }
        }
        return totalCount;
    }

    public double getTotalAmountSpent(User user) {
        List<Order> orders = orderRepository.findAllByCustomer(user);
        double totalAmount = 0.0;
        for (Order order : orders) {
            totalAmount += order.getOrderAmount();
        }
        return totalAmount;
    }

    public List<Product> getProductsByUser(User user) {
        List<Order> orders = orderRepository.findAllByCustomer(user);
        List<Product> products = new ArrayList<>();
        for (Order order : orders) {
            for (Order.OrderItem orderItem : order.getOrderItems()) {
                products.add(orderItem.getProduct());
            }
        }
        return products;
    }

    public List<Order> getOrdersByUser(Long userId) {
        User user = getUserById(userId);
        return orderRepository.findAllByCustomer(user);
    }
}
