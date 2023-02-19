package org.aston.ecommerce.order;

import org.aston.ecommerce.product.Product;
import org.aston.ecommerce.user.User;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

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

//    @Column(name = "Order_Date")
//    private LocalDate orderDate;

    @Column(name = "Quantity")
    private int quantity;
}




