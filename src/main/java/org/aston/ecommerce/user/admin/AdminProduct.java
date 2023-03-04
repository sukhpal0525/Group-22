package org.aston.ecommerce.user.admin;

import org.aston.ecommerce.product.ProductRepository;
import org.aston.ecommerce.product.Product;
import org.aston.ecommerce.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Controller
public class AdminProduct {

    private final ProductService productService;

    private final ProductRepository productRepository;

    @Autowired
    public AdminProduct(ProductRepository productRepository, ProductService productService) {
        this.productRepository = productRepository;
        this.productService = productService;
    }
    @GetMapping("/admin/products")
    public String displayProducts(Model model) {
        model.addAttribute("products", this.productService.findAll().get());
        return "admin_products";
    }

    @GetMapping("admin/products/get")
    public String getProducts(Model model, @RequestParam(name="searchQuery", required = false)String query) {
        List<Product> products = null;
        if(query == null) {
            //Display All
            Pageable limit = PageRequest.of(0,100);
            products = this.productService.search(limit);
        } else {
            //For future searching? if time
        }
        return "admin_products";
    }

    @GetMapping("/admin/products/{id}")
    public String getProduct(Model model, @PathVariable("id") String id) {
        Optional<Product> product = this.productRepository.findById(Long.parseLong(id));
        model.addAttribute("product",product.get());
        model.addAttribute("productId", id);
        return "admin_product_edit";
    }

    @PostMapping("/admin/products/{id}")
    public String updateProduct(@ModelAttribute("product") Product product) {
        productService.updateProduct(product);
        return "redirect:/admin/products";
    }

    @GetMapping("/admin/products/new")
    public String showAddForm(Model model) {
        model.addAttribute("product", new Product());
        return "admin_add_product";
    }

    @PostMapping("/admin/products")
    public String addProduct(@ModelAttribute("product") Product product) {
        productRepository.save(product);
        return "redirect:/admin/products";
    }

}
