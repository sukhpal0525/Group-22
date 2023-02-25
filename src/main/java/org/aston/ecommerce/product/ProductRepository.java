package org.aston.ecommerce.product;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long>, JpaRepository<Product, Long> {

    List<Product> findAllByCategory(Category category);
    List<Product> findAll();
    List<Product> findByNameContainingIgnoreCase(String query);
    @Query("SELECT p FROM Product p WHERE p.name LIKE %?1%")
    List<Product> findAllByName(String name);
}
