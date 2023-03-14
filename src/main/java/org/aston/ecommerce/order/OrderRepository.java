package org.aston.ecommerce.order;

import java.util.List;
import org.aston.ecommerce.user.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {

    List<Order> findAllByCustomerId(Long customerId);
    List<Order> findAllByCustomer(User user);
    List<Order> findAll();
}
