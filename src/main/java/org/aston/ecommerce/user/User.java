package org.aston.ecommerce.user;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.aston.ecommerce.basket.Basket;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import org.aston.ecommerce.product.Product;

@Entity
@Data
@Table(name = "WebUser")
public class User implements Serializable {

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserID")
    private Long id;

    @Column(nullable = false, unique = true, length = 45)
    private String username;

    @Column(nullable = false, unique = true, length = 45)
    private String email;

    @Column(nullable = false, length = 64)
    private String password;

    @Column(nullable = false, columnDefinition = "tinyint(1) default 0")
    private boolean isAdmin;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Basket basket = new Basket();

    public boolean isBasketEmpty() {
        return basket == null || basket.getBasketItems() == null || basket.getBasketItems().isEmpty();
    }

}