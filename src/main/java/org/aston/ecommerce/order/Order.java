package org.aston.ecommerce.order;

import org.aston.ecommerce.product.Product;
import org.aston.ecommerce.user.User;

import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Table(name = "WebOrder")
@Entity
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "UserID")
    private User customer;

    @ManyToOne
    @JoinColumn(name = "ProductID")
    private Product product;

    @CreationTimestamp
    @Column(name = "OrderDate")
    private LocalDateTime orderDate;

    @Column(name = "Quantity")
    private int quantity;
}




