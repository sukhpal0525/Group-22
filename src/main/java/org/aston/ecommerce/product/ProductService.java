package org.aston.ecommerce.product;

import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> search(String query) {
        return productRepository.findByNameContainingIgnoreCase(query);
    }

    public List<Product> search(Pageable pageRequest) {
        Page<Product> productsPage = this.productRepository.findAll(pageRequest);
        return productsPage.getContent();
    }

    public List<Product> findByCategory(String categoryStr) {
        Category category = Category.parseCategoryStr(categoryStr.toUpperCase());
        if (category != null) {
            return this.productRepository.findAllByCategory(category);
        }
        return null;
    }

    public Optional<List<Product>> findAll() {
        return Optional.of(this.productRepository.findAll());
    }

    public void updateProduct(Product product) {
        productRepository.save(product);
    }

}
