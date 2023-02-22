package org.aston.ecommerce.basket;

import lombok.Data;
import org.aston.ecommerce.product.Product;

import javax.persistence.*;

@Data
@Entity
@Table(name = "WebBasketItem")
public class BasketItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BasketItemID")
    private Long id;

    @Column(name = "Amount", nullable = false, columnDefinition = "INT default 0")
    private Integer amount;

    @ManyToOne
    @JoinColumn(name = "BasketID")
    private Basket basket;

    @ManyToOne
    @JoinColumn(name = "ProductID")
    private Product product;

}