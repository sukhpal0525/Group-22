package org.aston.ecommerce.product;

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
public class ProductRepositoryTests {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testProductAmount(){
        //Intel Core i9-11900K
        Optional<Product> optProduct = this.productRepository.findById(Long.parseLong("1"));
        Product editProduct = optProduct.get();
        Integer oldAmount = editProduct.getAmountAvailable();
        editProduct.setAmountAvailable(oldAmount + 5);
        this.productRepository.save(editProduct);

        Product foundProduct = this.entityManager.find(Product.class, editProduct.getId());

        assertThat(oldAmount + 5).isEqualTo(foundProduct.getAmountAvailable());

    }

    @Test
    public void testProductCategory(){
        //Intel Core i9-11900K
        Optional<Product> optProduct = this.productRepository.findById(Long.parseLong("1"));
        Product editProduct = optProduct.get();
        editProduct.setCategory(Category.MOUSE);
        this.productRepository.save(editProduct);

        Product foundProduct = this.entityManager.find(Product.class, editProduct.getId());

        assertNotEquals(foundProduct.getCategory(), Category.PROCESSOR);
        assertThat(editProduct.getCategory()).isEqualTo(Category.MOUSE);
    }


}
