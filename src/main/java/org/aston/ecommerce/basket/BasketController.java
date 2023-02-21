package org.aston.ecommerce.basket;

import org.aston.ecommerce.product.Product;
import org.aston.ecommerce.product.ProductRepository;
import org.aston.ecommerce.user.CustomUserDetails;
import org.aston.ecommerce.user.User;
import org.aston.ecommerce.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Controller
public class BasketController {

    @Autowired
    private BasketRepository basketRepository;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private ProductRepository productRepo;

    @GetMapping("/basket")
    public String viewBasket(Model model) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof CustomUserDetails) {
            String username = ((CustomUserDetails) principal).getUsername();
            User loggedInUser = userRepo.findByEmail(username);
            //See if the currently logged-in user has an empty basket or not
            if(loggedInUser.getBaskets().size() < 1){
                model.addAttribute("empty", "yes");
            }else{
                List<Basket> usersCurrentBasket = loggedInUser.getBaskets();
                model.addAttribute("listBaskets", usersCurrentBasket);
            }
        } else {
            model.addAttribute("isNotLoggedIn", "yes");
        }

        return "basket";
    }

    @GetMapping("/basket_delete/{id}")
    public String deleteBasket(@PathVariable("id") String id){

        Optional<Basket> optBasket = this.basketRepository.findById(Long.parseLong(id));
        Basket basket = optBasket.get();

        //Add stock number back to product before deleting the basket entry for good
        Product product = basket.getProduct();
        product.setAmountAvailable(product.getAmountAvailable() + basket.getAmount());

        this.productRepo.save(product);

        this.basketRepository.delete(basket);

        return "redirect:/basket";
    }

}
