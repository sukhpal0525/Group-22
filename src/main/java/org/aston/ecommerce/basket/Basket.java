package org.aston.ecommerce.basket;

import org.aston.ecommerce.product.Product;
import org.aston.ecommerce.user.User;

import javax.persistence.*;

@Entity
@Table(name = "WebBasket")
public class Basket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BasketID")
    private Long id;

    @Column(nullable = false, columnDefinition = "INT default 0")
    private Integer amount;

    @ManyToOne
    @JoinColumn(referencedColumnName = "ProductID")
    private Product product;

    @ManyToOne()
    @JoinColumn(name = "UserID")
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
