package org.aston.ecommerce.product.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.coyote.Response;
import org.aston.ecommerce.basket.Basket;
import org.aston.ecommerce.basket.BasketRepository;
import org.aston.ecommerce.product.*;
import org.aston.ecommerce.user.CustomUserDetails;
import org.aston.ecommerce.user.User;
import org.aston.ecommerce.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ProductResource {

    @Autowired private final ProductRepository productRepository;
    @Autowired private UserRepository userRepo;
    @Autowired private PurchaseService purchaseService;
    @Autowired private PurchaseService processRegister;
    @Autowired private BasketRepository basketRepository;

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
    public String getProducts(Model model, @PathVariable("category") String categoryStr) {
        List<Product> products = new ArrayList<>();
        Category category = Category.parseCategoryStr(categoryStr.toUpperCase());
        if (category != null) {
            products.addAll(this.productRepository.findAllByCategory(category));
        }
        model.addAttribute("products", products);

        return "products_list";
    }

    @GetMapping("/product/{id}")
    public String returnProduct(Model model, @PathVariable("id") String id) {

        Optional<Product> product = this.productRepository.findById(Long.parseLong(id));
        model.addAttribute("product", product.get());

        //For form submission
        model.addAttribute("purchase", new Purchase(id, "1"));

        return "product_display";
    }

    @PostMapping("/product_purchase")
    public String processRegister(Purchase purchase, BindingResult result, RedirectAttributes redirectAttrs) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        purchaseService.processPurchase(purchase, principal, redirectAttrs);
        return "redirect:/product/" + purchase.getProduct_id();
    }

    @GetMapping("/search")
    public String getSearch(Model model) {
        // Preload first 10 products for the search page
        if (!model.containsAttribute("products")) {
            Pageable limit = PageRequest.of(0, 10);
            Page<Product> products = this.productRepository.findAll(limit);

            model.addAttribute("products", products.getContent());
            model.addAttribute("searchQuery", "");
        }
        return "products_list";
    }

    @PostMapping("/search/products")
    public String searchProducts(
            @RequestParam(name = "searchQuery") String query, RedirectAttributes model) {

        if (query.trim().isEmpty()) {
            return "redirect:/search";
        }
        List<Product> products = productRepository.findByNameContainingIgnoreCase(query);
        model.addFlashAttribute("products", products);
        model.addFlashAttribute("searchQuery", query);

        return "redirect:/search";
    }

//    @GetMapping("/products/{query}")
//    public String searchProducts(Model model, @PathVariable("query") String query) {
//        List<Product> products = productRepository.findByNameContainingIgnoreCase(query);
//        model.addAttribute("product", products);
//        return "products_search";
//    }
//
//    @PostMapping("/product_search")
//    public String productSearch(Search search, BindingResult result, RedirectAttributes redirectAttrs, Model model) {
//
//        if(search.getSearch().trim().isEmpty()){
//            return "redirect:/products/list";
//        }
//
//        List<Product> foundProducts = this.productRepository.findAllByName(search.getSearch());
//
//        model.addAttribute("products", foundProducts);
//
//        model.addAttribute("search", new Search());
//        return "products_list";
//    }
}