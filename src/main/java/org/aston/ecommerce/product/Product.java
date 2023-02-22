package org.aston.ecommerce.product;

import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.aston.ecommerce.basket.Basket;

import java.util.List;

@Data
@Table(name = "WebProduct")
@Entity
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProductID")
    private Long id;

    @Column(name = "Name")
    private String name;

    @Column(name = "Description")
    private String description;

    @Column(name = "Category")
    @Enumerated(EnumType.ORDINAL)
    private Category category;

    @Column(name = "Amount")
    private Double amount;

    @Column(name = "AmountAvailable")
    private Integer amountAvailable;

//    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
//    private List<Basket> baskets;
}