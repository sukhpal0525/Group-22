package org.aston.ecommerce.product.web;

import java.util.List;
import java.util.Optional;

import org.aston.ecommerce.file.FileService;
import org.aston.ecommerce.product.Product;
import org.aston.ecommerce.product.ProductRepository;
import org.aston.ecommerce.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProductController {

    private final ProductRepository productRepository;
    private final ProductService productService;
    private final FileService fileService;

    @Autowired
    public ProductController(ProductRepository productRepository, ProductService productService, FileService fileService) {
        this.productRepository = productRepository;
        this.productService = productService;
        this.fileService = fileService;
    }

    @GetMapping("/products")
    private String getProducts(Model model) {
        model.addAttribute("products", this.productService.findAll().get());
        return "products_list";
    }

//    @GetMapping("/products")
//    public ResponseEntity getProducts() {
//        return ResponseEntity.of(this.productService.findAll());
//    }

    @GetMapping("/products/list/category")
    public String getProducts(Model model, @RequestParam(name = "categorySelect", required = false) String categoryStr) {


        List<Product> products = this.productService.findByCategory(categoryStr);

        if(products == null) products = this.productService.findAll().get();

        model.addAttribute("products", products);

        model.addAttribute("categorySelect", categoryStr);
        return "products_list";
    }

    @GetMapping("/product/{id}")
    public String returnProduct(Model model, @PathVariable("id") String id) {

        Optional<Product> optProduct = this.productRepository.findById(Long.parseLong(id));
        if(!optProduct.isPresent()) return "redirect:/products";
        Product product = optProduct.get();
        model.addAttribute("product", product);

        //For form submission
        model.addAttribute("productId", id);
        model.addAttribute("numOrdered", "1");

        model.addAttribute("productImage", this.fileService.getImageByName(product.getUrl()));

        return "product_display";
    }

    @GetMapping("/products/search")
    public String getSearch(Model model, @RequestParam(name = "searchQuery", required = false) String query) {

        List<Product> products = null;

        if (query == null) {
            // Preload first 10 products for the search page
            Pageable limit = PageRequest.of(0, 10);
            products = this.productService.search(limit);
        } else {
            products = this.productService.search(query);
        }
        model.addAttribute("products", products);
        model.addAttribute("searchQuery", query);

        return "products_list";
    }
}