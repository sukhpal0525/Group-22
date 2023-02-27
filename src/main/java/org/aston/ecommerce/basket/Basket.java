package org.aston.ecommerce.basket;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.aston.ecommerce.product.Product;
import org.aston.ecommerce.user.User;

@Entity
@Data
@ToString(exclude = "user")
@EqualsAndHashCode(exclude = "user")
@Table(name = "WebBasket")
public class Basket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BasketID")
    private Long id;

    @Column(columnDefinition = "INT default 0")
    private Integer amount;

    @OneToOne
    @JoinColumn(name = "UserID")
    private User user;

    @OneToMany(mappedBy = "basket", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BasketItem> basketItems = new ArrayList<>();

    public BasketItem getBasketItemForProduct(Product product) {
        return basketItems.stream()
                .filter((item) -> item.getProduct().equals(product))
                .findAny()
                .orElse(null);
    }

    public boolean containsProduct(Product product) {
        return basketItems.stream().anyMatch((item) -> item.getProduct().equals(product));
    }

}
