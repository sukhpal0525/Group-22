package org.aston.ecommerce.basket;

import org.aston.ecommerce.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BasketRepository extends CrudRepository<Basket, Long>, JpaRepository<Basket, Long> {

    void deleteAllByUser(User user);
    void deleteByUser(User user);
}
