package org.aston.ecommerce.product;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

    List<Product> findAllByCategory(Category category);

    List<Product> findAll();
}
