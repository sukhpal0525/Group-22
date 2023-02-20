package org.aston.ecommerce.basket;

import org.aston.ecommerce.user.CustomUserDetails;
import org.aston.ecommerce.user.User;
import org.aston.ecommerce.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BasketController {

    @Autowired
    private BasketRepository basketRepository;
    @Autowired
    private UserRepository userRepo;

    @GetMapping("/basket")
    public String viewBasket(Model model) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof CustomUserDetails) {
            String username = ((CustomUserDetails) principal).getUsername();
            User loggedInUser = userRepo.findByEmail(username);
            if(loggedInUser.getBaskets().size() < 1){
                model.addAttribute("empty", "yes");
            }
        } else {
            model.addAttribute("isNotLoggedIn", "yes");
        }


        return "basket";
    }

}
