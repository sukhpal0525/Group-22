package org.aston.ecommerce.product;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    //Return all products that are in stock yet have a stock level below 21 in descending order
    public List<Product> findAmberProducts(){
        List<Product> returnProducts = this.productRepository.findAll()
                .stream()
                .filter(c -> c.getAmountAvailable() <= 20 && c.getAmountAvailable() >= 1)
                .collect(Collectors.toList());
        Collections.sort(returnProducts, (Product p1, Product p2) -> p2.getAmountAvailable() - p1.getAmountAvailable());
        return returnProducts;
    }

    //Return all products that are out of stock
    public List<Product> findProductsOutOfStock(){
        List<Product> returnProducts = this.productRepository.findAll()
                .stream()
                .filter(c -> c.getAmountAvailable() < 1)
                .collect(Collectors.toList());
        return returnProducts;
    }

}
