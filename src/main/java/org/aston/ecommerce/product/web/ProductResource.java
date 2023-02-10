package org.aston.ecommerce.product.web;

import lombok.extern.slf4j.Slf4j;
import org.aston.ecommerce.product.Category;
import org.aston.ecommerce.product.Product;
import org.aston.ecommerce.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//@Slf4j
//@RestController
@Controller
@RequestMapping("/products")
public class ProductResource {

    private final ProductRepository productRepository;

    @Autowired
    public ProductResource(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/list")
    public ResponseEntity getProducts() {
        return ResponseEntity.of(Optional.of(this.productRepository.findAll()));
    }

    @GetMapping("/list/{category}")
    public ResponseEntity getProducts(
        @PathVariable("category") String categoryStr) {

        List<Product> products = new ArrayList<>();
        Category category = Category.parseCategoryStr(categoryStr.toUpperCase());
        if (category != null) {
            products.addAll(this.productRepository.findAllByCategory(category));
        }

        return ResponseEntity.of(Optional.of(products));
    }

    @GetMapping("/index")
    public String viewProducts() {
        return "products";
    }
}
