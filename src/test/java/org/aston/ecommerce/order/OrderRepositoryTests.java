package org.aston.ecommerce.order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class OrderRepositoryTests {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testOrderStatus(){
        //Status: UNPROCESSED (0)
        Optional<Order> optOrder = this.orderRepository.findById(Long.parseLong("1"));
        Order editOrder = optOrder.get();
        editOrder.setStatus(Status.FAILED);
        this.orderRepository.save(editOrder);

        Order foundOrder = this.entityManager.find(Order.class, editOrder.getId());

        assertNotEquals(editOrder.getStatus(), Status.UNPROCESSED);
        assertThat(editOrder.getStatus()).isEqualTo(Status.FAILED);
    }
}
