package org.aston.ecommerce.order;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {

    List<Order> findByCustomerId(Long customerId);
}
