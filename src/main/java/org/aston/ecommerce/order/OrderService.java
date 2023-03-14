package org.aston.ecommerce.order;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.aston.ecommerce.EcommerceApplication;
import org.aston.ecommerce.basket.Basket;
import org.aston.ecommerce.basket.BasketItem;
import org.aston.ecommerce.order.Order.OrderItem;
import org.aston.ecommerce.product.Product;
import org.aston.ecommerce.product.ProductRepository;
import org.aston.ecommerce.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderService {

    @Autowired private OrderRepository orderRepository;
    @Autowired private ProductRepository productRepository;

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

    //Return all orders in ASCENDING order based on their date grouped by their enum status such that the most recent orders are shown first
    public List<Order> findOrdersByStatusAndDate(){
        List<Order> returnOrders = this.orderRepository.findAll();
        Collections.sort(returnOrders, new Comparator<Order>() {
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
        return returnOrders;
    }

    //Return all orders that are unprocessed in DESCENDING order based on when they were made such that the oldest unprocessed order is shown first
    public List<Order> findUnprocessedOrders(){
        List<Order> returnOrders = this.orderRepository.findAll()
                .stream()
                .filter(o -> o.getStatus() == Status.UNPROCESSED)
                .collect(Collectors.toList());
        //DESCENDING
        Collections.sort(returnOrders, (Order o1, Order o2) -> o2.getOrderDate().compareTo(o1.getOrderDate()));
        return returnOrders;
    }
}