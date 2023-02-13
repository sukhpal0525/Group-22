package org.aston.ecommerce.product.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.aston.ecommerce.product.Category;
import org.aston.ecommerce.product.Product;
import org.aston.ecommerce.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ProductResource {

    private final ProductRepository productRepository;

    @Autowired
    public ProductResource(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/products/list")
    private String getProducts(Model model) {
        model.addAttribute("products", productRepository.findAll());
        return "products_list";
    }

    @GetMapping("/products")
    public ResponseEntity getProducts() {
        return ResponseEntity.of(Optional.of(this.productRepository.findAll()));
    }

    @GetMapping("/products/list/{category}")
    public ResponseEntity getProducts(
            @PathVariable("category") String categoryStr) {

        List<Product> products = new ArrayList<>();
        Category category = Category.parseCategoryStr(categoryStr.toUpperCase());
        if (category != null) {
            products.addAll(this.productRepository.findAllByCategory(category));
        }

        return ResponseEntity.of(Optional.of(products));
    }
}