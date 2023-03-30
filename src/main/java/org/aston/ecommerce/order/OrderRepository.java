package org.aston.ecommerce.order;

import java.time.LocalDateTime;
import java.util.List;
import org.aston.ecommerce.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {

    List<Order> findAllByCustomerId(Long customerId);
    List<Order> findAllByCustomer(User user);
    List<Order> findAllOrderByCustomer(User user);
    List<Order> findAll();
    List<Order> findByCreationDateAfter(LocalDateTime date);
    List<Order> findByOrderDateBetween(LocalDateTime startOfMonth, LocalDateTime now);

    @Query("SELECT o FROM Order o GROUP BY o.customer ORDER BY SUM(o.totalPrice) DESC")
    Page<Order> findAllOrderByCustomerDesc(Pageable pageable);

    default List<Order> findAllOrderByCustomerDesc() {
        Pageable limit = PageRequest.of(0, 5);
        return findAllOrderByCustomerDesc(limit).getContent();
    }
}
