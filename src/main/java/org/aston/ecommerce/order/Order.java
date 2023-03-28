package org.aston.ecommerce.order;

import java.util.ArrayList;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.aston.ecommerce.basket.BasketItem;
import org.aston.ecommerce.product.Product;
import org.aston.ecommerce.user.User;

import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@Table(name = "WebOrder")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OrderID", unique = true, nullable = false)
    private Long id;

    @Column(name = "TransactionNumber", unique = true, nullable = false)
    private String transactionNumber;

    @ManyToOne
    @JoinColumn(name = "UserID")
    private User customer;

    @CreationTimestamp
    @Column(name = "OrderDate")
    private LocalDateTime orderDate;

    @Column(name = "CreationDate")
    private LocalDateTime creationDate;

    @Column(name = "Quantity")
    private int quantity;

    @Column(name = "OrderAmount")
    private Double orderAmount;

    //E.g., mark as processed, mark as shipped
    @Column(name = "Status", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Status status = Status.UNPROCESSED;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<OrderItem> orderItems = new ArrayList<>();

    private transient List<OrderItem> rejectedItems = new ArrayList<>();

    @Data
    @Entity
    @ToString(exclude = "order")
    @EqualsAndHashCode(exclude = "order")
    @Table(name = "WebOrderItem")
    public static class OrderItem {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "OrderItemID")
        private Long id;

        @ManyToOne
        @JoinColumn(name = "OrderID")
        private Order order;

        @Column(name = "NumberOfItems")
        private Integer numOfItems;

        @ManyToOne
        @JoinColumn(name = "ProductID")
        private Product product;

        @Column(name = "Amount")
        private Double amount;
    }
}