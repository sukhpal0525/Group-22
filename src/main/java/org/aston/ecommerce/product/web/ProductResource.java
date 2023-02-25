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
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ProductResource {

    private final ProductRepository productRepository;
    @Autowired
    private BasketRepository basketRepository;
    @Autowired
    private UserRepository userRepo;

    @Autowired
    public ProductResource(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/products/list")
    private String getProducts(Model model) {
        model.addAttribute("products", productRepository.findAll());

        model.addAttribute("search", new Search());
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

        model.addAttribute("search", new Search());
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

        //See if user is logged in
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        //If user is not logged in, then send them to the login page because they need to be logged-in in order to add a product to their basket
        if (!(principal instanceof CustomUserDetails)) {
            return "redirect:/login";
        }

        Optional<Product> optProduct = this.productRepository.findById(Long.parseLong(purchase.getProduct_id()));
        Product product = optProduct.get();

        //Check to see if the user tried to order an amount larger than what is currently available in stock
        if (Integer.parseInt(purchase.getNum_ordered()) > product.getAmountAvailable()) {
            redirectAttrs.addFlashAttribute("purchase_fail", "Error! You tried to order more products than there is currently available in stock.");
        } else {
            product.setAmountAvailable(product.getAmountAvailable() - Integer.parseInt(purchase.getNum_ordered()));
            this.productRepository.save(product);

            //Find currently logged-in user
            String username = ((CustomUserDetails) principal).getUsername();
            User loggedInUser = userRepo.findByEmail(username);

            Basket addToBasket = new Basket();
            addToBasket.setAmount(Integer.parseInt(purchase.getNum_ordered()));
            addToBasket.setProduct(product);
            addToBasket.setUser(loggedInUser);

            this.basketRepository.save(addToBasket);

            redirectAttrs.addFlashAttribute("purchase_success", "yes");
        }

        return "redirect:/product/" + purchase.getProduct_id();
    }

    @GetMapping("/search")
    public String searchProducts(@RequestParam(name = "query") String query, Model model) {
        List<Product> products = productRepository.findByNameContainingIgnoreCase(query);
        model.addAttribute("products", products);
        return "search";
    }

    @GetMapping("/products/{query}")
    public String searchProducts(Model model, @PathVariable("query") String query) {
        List<Product> products = productRepository.findByNameContainingIgnoreCase(query);
        model.addAttribute("product", products);
        return "products_search";
    }

    @PostMapping("/product_search")
    public String productSearch(Search search, BindingResult result, RedirectAttributes redirectAttrs, Model model) {

        if(search.getSearch().trim().isEmpty()){
            return "redirect:/products/list";
        }

        List<Product> foundProducts = this.productRepository.findAllByName(search.getSearch());

        model.addAttribute("products", foundProducts);

        model.addAttribute("search", new Search());
        return "products_list";
    }
}